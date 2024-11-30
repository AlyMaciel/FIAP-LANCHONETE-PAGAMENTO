package com.postech.infra.persistence.repositories;

import com.postech.config.EmbeddedMongoConfig;
import com.postech.infra.persistence.entities.PagamentoEntity;
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
@SpringBootTest(classes = PagamentoRepository.class)
@EnableMongoRepositories
class PagamentoRepositoryIT {

    @Autowired
    private PagamentoRepository pagamentoRepository;

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
    void devePegarPagamentoPorIdPedido() {
        // Arrange
        var pagamento = TesteHelper.gerarPagamentoEntidade();

        pagamentoRepository.save(pagamento);

        // Act
        var pagamentoArmazenado = pagamentoRepository.getPagamentoEntityByIdPedido(pagamento.getIdPedido());

        pagamentoArmazenado.ifPresent(pagamentoEntity -> {
            assertThat(pagamentoEntity)
                .isInstanceOf(PagamentoEntity.class)
                .isNotNull()
                .isEqualTo(pagamento);
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getId)
                .isEqualTo(pagamento.getId());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getValor)
                .isEqualTo(pagamento.getValor());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getEstadoPagamento)
                .isEqualTo(pagamento.getEstadoPagamento());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getIdPedido)
                .isEqualTo(pagamento.getIdPedido());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getDataPagamento)
                .isEqualTo(pagamento.getDataPagamento());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getDataCriacaoPagamento)
                .isEqualTo(pagamento.getDataCriacaoPagamento());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getMetodoPagamento)
                .isEqualTo(pagamento.getMetodoPagamento());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getQrCode)
                .isEqualTo(pagamento.getQrCode());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getPagamentoId)
                .isEqualTo(pagamento.getPagamentoId());
            assertThat(pagamentoEntity)
                .extracting(PagamentoEntity::getTipoPagamento)
                .isEqualTo(pagamento.getTipoPagamento());
        });
    }

    @Test
    void devePegarPagamentoPorPagamentoId(){
        // Arrange
        var pagamento = TesteHelper.gerarPagamentoEntidade();

        pagamentoRepository.save(pagamento);

        // Act
        var pagamentoArmazenado = pagamentoRepository.getPagamentoEntityByIdPedido(pagamento.getIdPedido());

        pagamentoArmazenado.ifPresent(pagamentoEntity -> {
            assertThat(pagamentoEntity)
                    .isInstanceOf(PagamentoEntity.class)
                    .isNotNull()
                    .isEqualTo(pagamento);
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getId)
                    .isEqualTo(pagamento.getId());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getValor)
                    .isEqualTo(pagamento.getValor());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getEstadoPagamento)
                    .isEqualTo(pagamento.getEstadoPagamento());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getIdPedido)
                    .isEqualTo(pagamento.getIdPedido());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getDataPagamento)
                    .isEqualTo(pagamento.getDataPagamento());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getDataCriacaoPagamento)
                    .isEqualTo(pagamento.getDataCriacaoPagamento());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getMetodoPagamento)
                    .isEqualTo(pagamento.getMetodoPagamento());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getQrCode)
                    .isEqualTo(pagamento.getQrCode());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getPagamentoId)
                    .isEqualTo(pagamento.getPagamentoId());
            assertThat(pagamentoEntity)
                    .extracting(PagamentoEntity::getTipoPagamento)
                    .isEqualTo(pagamento.getTipoPagamento());
        });
    }

}