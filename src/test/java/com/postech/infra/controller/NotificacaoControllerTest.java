package com.postech.infra.controller;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.postech.application.client.PedidoClient;
import com.postech.application.usecases.NotificacaoUseCases;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificacaoControllerTest {

    @RegisterExtension
    LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(NotificacaoController.class);

    @Mock
    private NotificacaoUseCases notificacaoUseCases;

    private MockMvc mockMvc;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        var notificacaoController = new NotificacaoController(notificacaoUseCases);
        mockMvc = MockMvcBuilders.standaloneSetup(notificacaoController)
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
    void devePermitirAtualizarPagamento() throws Exception {
        var notificacaoPagamento = "notificacao";

         mockMvc.perform(post("/notificacoes/mercadopago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(notificacaoPagamento))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Notificação do estado do pagamento recebida com sucesso"));

        verify(notificacaoUseCases, times(1)).atualizaNotificacaoPagamento(notificacaoPagamento);
    }
}