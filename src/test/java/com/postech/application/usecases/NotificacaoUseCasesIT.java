package com.postech.application.usecases;

import com.postech.config.EmbeddedMongoConfig;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.interfaces.NotificacaoExternoInterface;
import com.postech.infra.gateways.RepositorioDePagamentoImpl;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import com.postech.utils.PagamentoHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

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

    @Autowired
    private NotificacaoUseCases notificacaoUseCases;

    @Autowired
    private RepositorioDePagamentoImpl repositorioDePagamento;

    @Autowired
    private MongodExecutable mongodExecutable;


    @BeforeEach
    void setUp() throws IOException {
        mongodExecutable.start();
    }

    @AfterEach
    void tearDown(){
        mongodExecutable.stop();
    }

    @Test
    void deveAtualizarStatusPagamento(){

        var pagamento = PagamentoHelper.gerarPagamento();

        repositorioDePagamento.salvaPagamento(pagamento);

        // Arrange
        var json= "{\"action\":\"payment.updated\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{\"id\":\"1\"}}";

        notificacaoUseCases.atualizaNotificacaoPagamento(json);

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