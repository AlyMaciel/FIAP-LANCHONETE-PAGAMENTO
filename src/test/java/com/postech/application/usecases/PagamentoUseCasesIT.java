package com.postech.application.usecases;

import com.postech.application.client.PedidoClient;
import com.postech.application.gateways.RepositorioDePagamentoGateway;
import com.postech.config.EmbeddedMongoConfig;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.utils.TesteHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
@SpringBootTest
@EnableMongoRepositories(basePackages = "com.postech.infra.persistence.repositories")
class PagamentoUseCasesIT {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RepositorioDePagamentoGateway repositorioDePagamentoImpl;

    @MockBean
    private PedidoClient pedidoCliente;

    @Autowired
    private PagamentoUseCases pagamentoUseCases;


    @BeforeAll
    static void startMongo(@Autowired MongodExecutable mongodExecutable) throws Exception {
        if (mongodExecutable != null) {
            mongodExecutable.start();
        }
    }

    @AfterEach
    public void limpaBanco() {
        mongoTemplate.getDb().drop();  // Limpa o banco de dados
    }

    @AfterAll
    static void stopMongo(@Autowired MongodExecutable mongodExecutable) {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @Test
    void deveCriarPagamentoPix(){
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        var pagamentoSalvo = pagamentoUseCases.criarPagamentoPix(criarPagamentoRequestDTO);

        assertThat(pagamentoSalvo)
                .isInstanceOf(Pagamento.class)
                .isNotNull();

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getValor)  // Extrai o valor
                .isEqualTo(criarPagamentoRequestDTO.getValorTotal());

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
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();
        criarPagamentoRequestDTO.setValorTotal(null);

        assertThatThrownBy(() -> pagamentoUseCases.criarPagamentoPix(criarPagamentoRequestDTO))
                .isInstanceOf(PagamentoException.class);

    }

    @Test
    void devePegarStatusPorIdPedido() {
        var pagamento = TesteHelper.gerarPagamento();

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
        var pagamento = TesteHelper.gerarPagamento();

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
        var pagamento = TesteHelper.gerarPagamento();

        repositorioDePagamentoImpl.salvaPagamento(pagamento);

        var notificacaoPagamento = TesteHelper.gerarNotificacaoPagamentoPago();

        pagamentoUseCases.atualizaEstadoPagamento(notificacaoPagamento);

        verify(pedidoCliente).enviaEstadoPagamento(1L, EstadoPagamentoEnum.PAGO, notificacaoPagamento.getDataAttPagamento());

        var pagamentoSalvo = repositorioDePagamentoImpl.consultaPagamentoPorIdPagamento(notificacaoPagamento.getPagamentoId());

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(notificacaoPagamento.getEstadoPagamento());

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataPagamento)
                .isEqualTo(notificacaoPagamento.getDataAttPagamento());
    }

}
