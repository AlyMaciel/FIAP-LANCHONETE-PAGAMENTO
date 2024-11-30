package com.postech.infra.gateways;

import com.postech.config.EmbeddedMongoConfig;
import com.postech.domain.entities.Pagamento;
import com.postech.utils.TesteHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;


@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
@SpringBootTest
@EnableMongoRepositories(basePackages = "com.postech.infra.persistence.repositories")
class RepositorioDePagamentoImplIT {


    @Autowired
    private RepositorioDePagamentoImpl repositorioDePagamentoImpl;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    static void startMongo(@Autowired MongodExecutable mongodExecutable) throws Exception {
        if (mongodExecutable != null) {
            mongodExecutable.start();
        }
    }

    @AfterAll
    static void stopMongo(@Autowired MongodExecutable mongodExecutable) {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();  // Limpa o banco de dados
    }


    @Test
    void deveSalvarPagamento(){
        Pagamento pagamento = TesteHelper.gerarPagamento();

        var pagamentoSalvo = repositorioDePagamentoImpl.salvaPagamento(pagamento);

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
        Pagamento pagamento = TesteHelper.gerarPagamento();

        repositorioDePagamentoImpl.salvaPagamento(pagamento);

        var pagamentoConsultado = repositorioDePagamentoImpl.consultaPagamentoPorIdPedido(pagamento.getIdPedido());

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
        Pagamento pagamento = TesteHelper.gerarPagamento();

        var pagamentoConsultado = repositorioDePagamentoImpl.consultaPagamentoPorIdPedido(pagamento.getIdPedido());

        assertThat(pagamentoConsultado)
                .isNull();
    }

    @Test
    void deveConsultarPagamentoPorIdPagamento(){
        Pagamento pagamento = TesteHelper.gerarPagamento();

        repositorioDePagamentoImpl.salvaPagamento(pagamento);

        var pagamentoConsultado = repositorioDePagamentoImpl.consultaPagamentoPorIdPagamento(pagamento.getPagamentoId());

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
        Pagamento pagamento = TesteHelper.gerarPagamento();

        var pagamentoConsultado = repositorioDePagamentoImpl.consultaPagamentoPorIdPagamento(pagamento.getPagamentoId());

        assertThat(pagamentoConsultado)
                .isNull();
    }
}
