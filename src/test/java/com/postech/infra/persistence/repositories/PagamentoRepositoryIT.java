package com.postech.infra.persistence.repositories;

import com.postech.config.EmbeddedMongoConfig;
import com.postech.infra.persistence.entities.PagamentoEntity;
import com.postech.utils.PagamentoHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
@SpringBootTest(classes = PagamentoRepository.class)
@EnableMongoRepositories
class PagamentoRepositoryIT {

    @Autowired
    private MongodExecutable mongodExecutable;

    @Autowired
    private PagamentoRepository pagamentoRepository;


    @BeforeEach
    void setUp() throws IOException {
        mongodExecutable.start();

    }

    @AfterEach
    void tearDown() {
        mongodExecutable.stop();
    }

    @Test
    void devePegarPagamentoPorIdPedido() {
        // Arrange
        var pagamento = PagamentoHelper.gerarPagamentoEntidade();

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
        var pagamento = PagamentoHelper.gerarPagamentoEntidade();

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