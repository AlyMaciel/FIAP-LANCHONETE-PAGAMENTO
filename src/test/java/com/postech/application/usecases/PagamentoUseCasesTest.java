package com.postech.application.usecases;

import com.postech.application.gateways.RepositorioDePagamentoGateway;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.ErroPagamentoEnum;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.domain.interfaces.PagamentoInterface;
import com.postech.infra.dto.request.ClienteRequestDTO;
import com.postech.infra.dto.request.PedidoRequestDTO;
import com.postech.utils.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

class PagamentoUseCasesTest {


    @Mock
    private RepositorioDePagamentoGateway repositorio;

    @Mock
    private PagamentoInterface pagamentoExternoUseCase;

    @InjectMocks
    private PagamentoUseCases pagamentoUseCases;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void deveCriarPagamentoPix(){
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(1L, BigDecimal.valueOf(5.0), new ClienteRequestDTO("teste@teste.com", "João", "12345678901"));

        Pagamento pagamento = PagamentoHelper.gerarPagamento();

        when(pagamentoExternoUseCase.criarPagamento(pedidoRequestDTO)).thenReturn(pagamento);

        when(repositorio.salvaPagamento(pagamento)).thenReturn(pagamento);

        Pagamento pagamentoSalvo = pagamentoUseCases.criarPagamentoPix(pedidoRequestDTO);

        verify(pagamentoExternoUseCase, times(1)).criarPagamento(pedidoRequestDTO);

        verify(repositorio, times(1)).salvaPagamento(pagamento);

        assertThat(pagamentoSalvo)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getId)
                .isEqualTo(pagamento.getId());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getValor)
                .isEqualTo(pagamento.getValor());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(pagamento.getEstadoPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getIdPedido)
                .isEqualTo(pagamento.getIdPedido());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(pagamento.getDataPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataCriacaoPagamento)
                .isEqualTo(pagamento.getDataCriacaoPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getMetodoPagamento)
                .isEqualTo(pagamento.getMetodoPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getQrCode)
                .isEqualTo(pagamento.getQrCode());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getPagamentoId)
                .isEqualTo(pagamento.getPagamentoId());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getTipoPagamento)
                .isEqualTo(pagamento.getTipoPagamento());


    }

    @Test
    void deveLancarException_QuandoDerErro_AoCriarPagamentoPix() {
        var pedidoRequestDTO = new PedidoRequestDTO(1L, BigDecimal.valueOf(5.0), new ClienteRequestDTO("teste@teste.com", "João", "12345678901"));

        var pagamento = PagamentoHelper.gerarPagamento();

        when(pagamentoExternoUseCase.criarPagamento(pedidoRequestDTO)).thenThrow(new PagamentoException(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO));

        assertThatThrownBy(() -> pagamentoUseCases.criarPagamentoPix(pedidoRequestDTO))
                .isInstanceOf(PagamentoException.class);

        verify(pagamentoExternoUseCase, times(1)).criarPagamento(pedidoRequestDTO);

        verify(repositorio, never()).salvaPagamento(pagamento);
    }

    @Test
    void devePegarStatusPorIdPedido() {
        var pagamento = PagamentoHelper.gerarPagamento();

        when(repositorio.consultaPagamentoPorIdPedido(any(Long.class))).thenReturn(pagamento);

        var statusPagamento = pagamentoUseCases.getStatusPagamento(pagamento.getIdPedido());

        verify(repositorio, times(1)).consultaPagamentoPorIdPedido(pagamento.getIdPedido());

        assertThat(statusPagamento)
                .isInstanceOf(EstadoPagamentoEnum.class)
                .isNotNull()
                .isEqualTo(pagamento.getEstadoPagamento());
    }


    @Test
    void deveLancarException_QuandoNaoEncontrarStatusPagamentoPorIdPedido() {
        var pagamento = PagamentoHelper.gerarPagamento();

        when(repositorio.consultaPagamentoPorIdPedido(any(Long.class))).thenReturn(null);

        Assertions.assertThrows(PagamentoException.class, () -> pagamentoUseCases.getStatusPagamento(pagamento.getIdPedido()));

        verify(repositorio, times(1)).consultaPagamentoPorIdPedido(pagamento.getIdPedido());
    }

    @Test
    void devePegarPagamentoPorIdPagamento() {
        var pagamento = PagamentoHelper.gerarPagamento();

        when(repositorio.consultaPagamentoPorIdPagamento(any(String.class))).thenReturn(pagamento);

        var pagamentoSalvo = pagamentoUseCases.getPagamentoPorIdPagamento(pagamento.getPagamentoId());

        verify(repositorio, times(1)).consultaPagamentoPorIdPagamento(pagamento.getPagamentoId());

       assertThat(pagamentoSalvo)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getId)
                .isEqualTo(pagamento.getId());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getValor)
                .isEqualTo(pagamento.getValor());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(pagamento.getEstadoPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getIdPedido)
                .isEqualTo(pagamento.getIdPedido());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(pagamento.getDataPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataCriacaoPagamento)
                .isEqualTo(pagamento.getDataCriacaoPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getMetodoPagamento)
                .isEqualTo(pagamento.getMetodoPagamento());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getQrCode)
                .isEqualTo(pagamento.getQrCode());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getPagamentoId)
                .isEqualTo(pagamento.getPagamentoId());
        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getTipoPagamento)
                .isEqualTo(pagamento.getTipoPagamento());


    }

    @Test
    void deveLancarException_QuandoNaoEncontrarPagamentoPorIdPagamento() {
        var pagamento = PagamentoHelper.gerarPagamento();

        when(repositorio.consultaPagamentoPorIdPagamento(any(String.class))).thenReturn(null);

        Assertions.assertThrows(PagamentoException.class, () -> pagamentoUseCases.getPagamentoPorIdPagamento(pagamento.getPagamentoId()));

        verify(repositorio, times(1)).consultaPagamentoPorIdPagamento(pagamento.getPagamentoId());

    }

    @Test
    void deveAtualizarEstadoPagamento(){
        var pagamento = PagamentoHelper.gerarPagamento();

        var notificacaoPagamento = PagamentoHelper.gerarNotificacaoPagamentoPago();

        when(repositorio.consultaPagamentoPorIdPagamento(any(String.class))).thenReturn(pagamento);

        when(repositorio.salvaPagamento(pagamento)).thenReturn(pagamento);

        pagamentoUseCases.atualizaEstadoPagamento(notificacaoPagamento);

        verify(repositorio, times(1)).consultaPagamentoPorIdPagamento(notificacaoPagamento.getPagamentoId());

        verify(repositorio, times(1)).salvaPagamento(pagamento);

        assertThat(pagamento)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(notificacaoPagamento.getEstadoPagamento());

        assertThat(pagamento)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(notificacaoPagamento.getDataAttPagamento());
    }

}