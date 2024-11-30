package com.postech.application.usecases;

import com.postech.application.client.PedidoClient;
import com.postech.config.EmbeddedMongoConfig;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.interfaces.NotificacaoExternoInterface;
import com.postech.infra.gateways.RepositorioDePagamentoImpl;
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

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
@SpringBootTest
@EnableMongoRepositories(basePackages = "com.postech.infra.persistence.repositories")
class NotificacaoUseCasesIT {

    @Autowired
    private NotificacaoExternoInterface notificacaoExternoInterface;

    @Autowired
    private PagamentoUseCases pagamentoUseCases;

    @MockBean
    private PedidoClient pedidoCliente;

    @Autowired
    private NotificacaoUseCases notificacaoUseCases;

    @Autowired
    private RepositorioDePagamentoImpl repositorioDePagamento;

    @Autowired
    private MongoTemplate mongoTemplate;


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
    void deveAtualizarStatusPagamento(){

        var pagamento = TesteHelper.gerarPagamento();

        repositorioDePagamento.salvaPagamento(pagamento);

        // Arrange
        var json= "{\"action\":\"payment.updated\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{\"id\":\"1\"}}";

        notificacaoUseCases.atualizaNotificacaoPagamento(json);

        verify(pedidoCliente, times(1)).enviaEstadoPagamento(1L, EstadoPagamentoEnum.PAGO, LocalDate.of(2024, Month.JULY, 29));

        var pagamentoSalvo = repositorioDePagamento.consultaPagamentoPorIdPagamento("1");

        assertThat(pagamentoSalvo)
                .isInstanceOf(Pagamento.class)
                .isNotNull();

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getEstadoPagamento)
                .isEqualTo(EstadoPagamentoEnum.PAGO);

        assertThat(pagamentoSalvo)
                .extracting(Pagamento::getDataPagamento)
                .isNotNull();

    }


}