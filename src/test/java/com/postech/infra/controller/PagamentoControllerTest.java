package com.postech.infra.controller;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.application.usecases.PagamentoUseCases;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.ErroPagamentoEnum;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.infra.dto.request.CriarPagamentoRequestDTO;
import com.postech.infra.handler.PagamentoAdvice;
import com.postech.infra.mappers.PagamentoMapper;
import com.postech.utils.TesteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PagamentoControllerTest {

    @RegisterExtension
    LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(PagamentoController.class);

    @Mock
    private PagamentoUseCases pagamentoUseCases;

    @Mock
    private PagamentoMapper mapper;

    private MockMvc mockMvc;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PagamentoController pagamentoController = new PagamentoController(pagamentoUseCases, mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController)
                .setControllerAdvice(new PagamentoAdvice())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarPagamento() throws Exception {

        var pagamento = TesteHelper.gerarPagamento();
        when(pagamentoUseCases.criarPagamentoPix(any(CriarPagamentoRequestDTO.class))).thenReturn(pagamento);

        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        var pagamentoResponseDTO = TesteHelper.gerarPagamentoResponse();
        when(mapper.paraResponseDTO(any(Pagamento.class))).thenReturn(pagamentoResponseDTO);

        mockMvc.perform(post("/pagamentos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(criarPagamentoRequestDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.valor").value(pagamentoResponseDTO.getValor().toString()))
                    .andExpect(jsonPath("$.estadoPagamento").value(pagamentoResponseDTO.getEstadoPagamento().toString()))
                    .andExpect(jsonPath("$.dataPagamento").exists())
                    .andExpect(jsonPath("$.dataCriacaoPagamento").exists())
                    .andExpect(jsonPath("$.tipoPagamento").value(pagamentoResponseDTO.getTipoPagamento().toString()))
                    .andExpect(jsonPath("$.metodoPagamento").value(pagamentoResponseDTO.getMetodoPagamento().toString()))
                    .andExpect(jsonPath("$.qrCode").value(pagamentoResponseDTO.getQrCode()))
                    .andExpect(jsonPath("$.pagamentoId").value(pagamentoResponseDTO.getPagamentoId()));


        verify(pagamentoUseCases, times(1)).criarPagamentoPix(any(CriarPagamentoRequestDTO.class));
    }

    @Test
    void deveGerarExcecao_QuandoDerErroAoGerarPagamento() throws Exception {


        when(pagamentoUseCases.criarPagamentoPix(any(CriarPagamentoRequestDTO.class))).thenThrow(new PagamentoException(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO));

        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(criarPagamentoRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO.toString()))
                .andExpect(jsonPath("$.detalhe").value(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO.getDetalhe()));

        verify(pagamentoUseCases, times(1)).criarPagamentoPix(any(CriarPagamentoRequestDTO.class));
    }

    @Test
    void devePermitirPegarEstadoPagamento() throws Exception {

        when(pagamentoUseCases.getStatusPagamento(anyLong())).thenReturn(EstadoPagamentoEnum.PENDENTE_PAGAMENTO);

        mockMvc.perform(get("/pagamentos/{idPedido}", 1)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(EstadoPagamentoEnum.PENDENTE_PAGAMENTO.toString()));

        verify(pagamentoUseCases, times(1)).getStatusPagamento(anyLong());
    }

    @Test
    void deveGerarExcecao_QuandoNaoAcharPagamento_PorIdPedido() throws Exception {

        when(pagamentoUseCases.getStatusPagamento(anyLong())).thenThrow(new PagamentoException(ErroPagamentoEnum.PAGAMENTO_NAO_ENCONTRADO_POR_ID_PEDIDO));

        mockMvc.perform(get("/pagamentos/{idPedido}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value(ErroPagamentoEnum.PAGAMENTO_NAO_ENCONTRADO_POR_ID_PEDIDO.toString()))
                .andExpect(jsonPath("$.detalhe").value(ErroPagamentoEnum.PAGAMENTO_NAO_ENCONTRADO_POR_ID_PEDIDO.getDetalhe()));

        verify(pagamentoUseCases, times(1)).getStatusPagamento(anyLong());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}