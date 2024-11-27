package com.postech.application.usecases;

import com.postech.application.gateways.RepositorioDePagamentoGateway;
import com.postech.config.EmbeddedMongoConfig;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.infra.dto.request.ClienteRequestDTO;
import com.postech.infra.dto.request.PedidoRequestDTO;
import com.postech.utils.PagamentoHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
@SpringBootTest
@EnableMongoRepositories(basePackages = "com.postech.infra.persistence.repositories")
class PagamentoUseCasesIT {

    @Autowired
    private MongodExecutable mongodExecutable;

    @Autowired
    private RepositorioDePagamentoGateway repositorioDePagamentoImpl;

    @Autowired
    private PagamentoUseCases pagamentoUseCases;

    @BeforeEach
    void setUp() throws IOException {
        mongodExecutable.start();

    }

    @AfterEach
    void tearDown() {
        mongodExecutable.stop();
    }

    @Test
    void deveCriarPagamentoPix(){
        var pedidoRequestDTO = PagamentoHelper.gerarPedidoRequest();

        var pagamentoSalvo = pagamentoUseCases.criarPagamentoPix(pedidoRequestDTO);

        assertThat(pagamentoSalvo)
                .isInstanceOf(Pagamento.class)
                .isNotNull();

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getValor)
                .isEqualTo(pedidoRequestDTO.getValorTotal());

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(EstadoPagamentoEnum.PENDENTE_PAGAMENTO);

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getPagamentoId)
                .isNotNull();

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataPagamento)
                .isNull();

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataCriacaoPagamento)
                .isNotNull();

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getMetodoPagamento)
                .isEqualTo(TipoMetodoPagamento.PIX);

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getQrCode)
                .isNotNull();

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getTipoPagamento)
                .isEqualTo(TipoPagamentoEnum.MERCADO_PAGO);
    }

    @Test
    void deveLancarException_QuandoDerErro_AoCriarPagamentoPix() {
        var pedidoRequestDTO = new PedidoRequestDTO(1L, null, new ClienteRequestDTO("teste@teste.com", "Test", "19119119100"));

        assertThatThrownBy(() -> pagamentoUseCases.criarPagamentoPix(pedidoRequestDTO))
                .isInstanceOf(PagamentoException.class);

    }

    @Test
    void devePegarStatusPorIdPedido() {
        var pagamento = PagamentoHelper.gerarPagamento();

        repositorioDePagamentoImpl.salvaPagamento(pagamento);

        var statusPagamento = pagamentoUseCases.getStatusPagamento(pagamento.getIdPedido());

        assertThat(statusPagamento)
                .isInstanceOf(EstadoPagamentoEnum.class)
                .isNotNull()
                .isEqualTo(pagamento.getEstadoPagamento());
    }

    @Test
    void deveLancarException_QuandoNaoEncontrarStatusPagamentoPorIdPedido() {
        Assertions.assertThrows(PagamentoException.class, () -> pagamentoUseCases.getStatusPagamento(1L));
    }

    @Test
    void devePegarPagamentoPorIdPagamento() {
        var pagamento = PagamentoHelper.gerarPagamento();

        repositorioDePagamentoImpl.salvaPagamento(pagamento);

        var pagamentoSalvo = pagamentoUseCases.getPagamentoPorIdPagamento(pagamento.getPagamentoId());

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
        Assertions.assertThrows(PagamentoException.class, () -> pagamentoUseCases.getPagamentoPorIdPagamento("erro"));
    }

    @Test
    void deveAtualizarEstadoPagamento(){
        var pagamento = PagamentoHelper.gerarPagamento();

        repositorioDePagamentoImpl.salvaPagamento(pagamento);

        var notificacaoPagamento = PagamentoHelper.gerarNotificacaoPagamentoPago();

        pagamentoUseCases.atualizaEstadoPagamento(notificacaoPagamento);

        var pagamentoSalvo = repositorioDePagamentoImpl.consultaPagamentoPorIdPagamento(notificacaoPagamento.getPagamentoId());

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(notificacaoPagamento.getEstadoPagamento());

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(notificacaoPagamento.getDataAttPagamento());
    }

}
