package com.postech.application.usecases;

import com.postech.application.client.PedidoClient;
import com.postech.application.gateways.RepositorioDePagamentoGateway;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.ErroPagamentoEnum;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.domain.interfaces.PagamentoInterface;
import com.postech.infra.dto.request.CriarPagamentoRequestDTO;
import com.postech.utils.TesteHelper;
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

class PagamentoUseCasesTest {


    @Mock
    private RepositorioDePagamentoGateway repositorio;

    @Mock
    private PagamentoInterface pagamentoExternoUseCase;

    @Mock
    private PedidoClient pedidoClient;

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
        CriarPagamentoRequestDTO criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        Pagamento pagamento = TesteHelper.gerarPagamento();

        when(pagamentoExternoUseCase.criarPagamento(criarPagamentoRequestDTO)).thenReturn(pagamento);

        when(repositorio.salvaPagamento(pagamento)).thenReturn(pagamento);

        Pagamento pagamentoSalvo = pagamentoUseCases.criarPagamentoPix(criarPagamentoRequestDTO);

        verify(pagamentoExternoUseCase, times(1)).criarPagamento(criarPagamentoRequestDTO);

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
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        var pagamento = TesteHelper.gerarPagamento();

        when(pagamentoExternoUseCase.criarPagamento(criarPagamentoRequestDTO)).thenThrow(new PagamentoException(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO));

        assertThatThrownBy(() -> pagamentoUseCases.criarPagamentoPix(criarPagamentoRequestDTO))
                .isInstanceOf(PagamentoException.class);

        verify(pagamentoExternoUseCase, times(1)).criarPagamento(criarPagamentoRequestDTO);

        verify(repositorio, never()).salvaPagamento(pagamento);
    }

    @Test
    void devePegarStatusPorIdPedido() {
        var pagamento = TesteHelper.gerarPagamento();

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
        var pagamento = TesteHelper.gerarPagamento();

        when(repositorio.consultaPagamentoPorIdPedido(any(Long.class))).thenReturn(null);

        Assertions.assertThrows(PagamentoException.class, () -> pagamentoUseCases.getStatusPagamento(pagamento.getIdPedido()));

        verify(repositorio, times(1)).consultaPagamentoPorIdPedido(pagamento.getIdPedido());
    }

    @Test
    void devePegarPagamentoPorIdPagamento() {
        var pagamento = TesteHelper.gerarPagamento();

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
        var pagamento = TesteHelper.gerarPagamento();

        when(repositorio.consultaPagamentoPorIdPagamento(any(String.class))).thenReturn(null);

        Assertions.assertThrows(PagamentoException.class, () -> pagamentoUseCases.getPagamentoPorIdPagamento(pagamento.getPagamentoId()));

        verify(repositorio, times(1)).consultaPagamentoPorIdPagamento(pagamento.getPagamentoId());

    }

    @Test
    void deveAtualizarEstadoPagamento(){
        var pagamento = TesteHelper.gerarPagamento();

        var notificacaoPagamento = TesteHelper.gerarNotificacaoPagamentoPago();

        when(repositorio.consultaPagamentoPorIdPagamento(any(String.class))).thenReturn(pagamento);

        when(repositorio.salvaPagamento(pagamento)).thenReturn(pagamento);

        pagamentoUseCases.atualizaEstadoPagamento(notificacaoPagamento);

        verify(repositorio, times(1)).consultaPagamentoPorIdPagamento(notificacaoPagamento.getPagamentoId());

        verify(repositorio, times(1)).salvaPagamento(pagamento);

        verify(pedidoClient, times(1)).enviaEstadoPagamento(1L, EstadoPagamentoEnum.PAGO, notificacaoPagamento.getDataAttPagamento());

        assertThat(pagamento)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(notificacaoPagamento.getEstadoPagamento());

        assertThat(pagamento)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(notificacaoPagamento.getDataAttPagamento());
    }

}