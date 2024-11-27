package com.postech.infra.gateways;

import com.postech.domain.entities.Pagamento;
import com.postech.infra.mappers.PagamentoMapper;
import com.postech.infra.persistence.entities.PagamentoEntity;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import com.postech.utils.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RepositorioDePagamentoImplTest {

    @Mock
    private PagamentoRepository repositorio;

    @Mock
    private PagamentoMapper mapper;

    @InjectMocks
    private RepositorioDePagamentoImpl repositorioDePagamento;

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
    void deveSalvarPagamento(){
        Pagamento pagamento = PagamentoHelper.gerarPagamento();

        PagamentoEntity pagamentoEntity = PagamentoHelper.gerarPagamentoEntidade();

        when(mapper.paraEntidade(any(Pagamento.class))).thenReturn(pagamentoEntity);

        when(repositorio.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);

        when(mapper.paraDominio(any(PagamentoEntity.class))).thenReturn(pagamento);

        var pagamentoSalvo = repositorioDePagamento.salvaPagamento(pagamento);

        verify(repositorio, times(1)).save(any(PagamentoEntity.class));

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
    void deveConsultarPagamentoPorIdPedido() {
        Pagamento pagamento = PagamentoHelper.gerarPagamento();

        PagamentoEntity pagamentoEntity = PagamentoHelper.gerarPagamentoEntidade();

        when(repositorio.getPagamentoEntityByIdPedido(any(Long.class))).thenReturn(Optional.of(pagamentoEntity));

        when(mapper.paraDominio(any(PagamentoEntity.class))).thenReturn(pagamento);

        var pagamentoConsultado = repositorioDePagamento.consultaPagamentoPorIdPedido(pagamento.getIdPedido());

        verify(repositorio, times(1)).getPagamentoEntityByIdPedido(pagamento.getIdPedido());

        assertThat(pagamentoConsultado)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);

        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getId)
                .isEqualTo(pagamento.getId());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getValor)
                .isEqualTo(pagamento.getValor());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(pagamento.getEstadoPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getIdPedido)
                .isEqualTo(pagamento.getIdPedido());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(pagamento.getDataPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getDataCriacaoPagamento)
                .isEqualTo(pagamento.getDataCriacaoPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getMetodoPagamento)
                .isEqualTo(pagamento.getMetodoPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getQrCode)
                .isEqualTo(pagamento.getQrCode());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getPagamentoId)
                .isEqualTo(pagamento.getPagamentoId());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getTipoPagamento)
                .isEqualTo(pagamento.getTipoPagamento());
        assertThat(pagamentoConsultado)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);

    }

    @Test
    void deveRetornarNulo_QuandoConsultarPagamento_IdPedidoNaoExistente() {
        Pagamento pagamento = PagamentoHelper.gerarPagamento();

        when(repositorio.getPagamentoEntityByIdPedido(any(Long.class))).thenReturn(Optional.empty());

        when(mapper.paraDominio(any(PagamentoEntity.class))).thenReturn(pagamento);

        var pagamentoConsultado = repositorioDePagamento.consultaPagamentoPorIdPedido(pagamento.getIdPedido());

        verify(repositorio, times(1)).getPagamentoEntityByIdPedido(pagamento.getIdPedido());

        assertThat(pagamentoConsultado)
                .isNull();
    }

    @Test
    void deveConsultarPagamentoPorIdPagamento(){
        Pagamento pagamento = PagamentoHelper.gerarPagamento();

        PagamentoEntity pagamentoEntity = PagamentoHelper.gerarPagamentoEntidade();

        when(repositorio.getPagamentoEntityByPagamentoId(any(String.class))).thenReturn(Optional.of(pagamentoEntity));

        when(mapper.paraDominio(any(PagamentoEntity.class))).thenReturn(pagamento);

        var pagamentoConsultado = repositorioDePagamento.consultaPagamentoPorIdPagamento(pagamento.getPagamentoId());

        verify(repositorio, times(1)).getPagamentoEntityByPagamentoId(pagamento.getPagamentoId());

        assertThat(pagamentoConsultado)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);

        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getId)
                .isEqualTo(pagamento.getId());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getValor)
                .isEqualTo(pagamento.getValor());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(pagamento.getEstadoPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getIdPedido)
                .isEqualTo(pagamento.getIdPedido());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(pagamento.getDataPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getDataCriacaoPagamento)
                .isEqualTo(pagamento.getDataCriacaoPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getMetodoPagamento)
                .isEqualTo(pagamento.getMetodoPagamento());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getQrCode)
                .isEqualTo(pagamento.getQrCode());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getPagamentoId)
                .isEqualTo(pagamento.getPagamentoId());
        assertThat(pagamentoConsultado)
                .extracting(Pagamento::getTipoPagamento)
                .isEqualTo(pagamento.getTipoPagamento());
        assertThat(pagamentoConsultado)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);

    }

    @Test
    void deveRetornarNulo_QuandoConsultarPagamento_IdPagamentoNaoExistente() {
        Pagamento pagamento = PagamentoHelper.gerarPagamento();

        when(repositorio.getPagamentoEntityByPagamentoId(any(String.class))).thenReturn(Optional.empty());

        when(mapper.paraDominio(any(PagamentoEntity.class))).thenReturn(pagamento);

        var pagamentoConsultado = repositorioDePagamento.consultaPagamentoPorIdPagamento(pagamento.getPagamentoId());

        verify(repositorio, times(1)).getPagamentoEntityByPagamentoId(pagamento.getPagamentoId());

        assertThat(pagamentoConsultado)
                .isNull();
    }


}