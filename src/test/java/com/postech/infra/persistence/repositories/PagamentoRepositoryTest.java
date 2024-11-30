package com.postech.infra.persistence.repositories;

import com.postech.infra.persistence.entities.PagamentoEntity;
import com.postech.utils.TesteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PagamentoRepositoryTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

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
    void devePegarPagamentoPorIdPedido() {
        // Arrange
        var pagamento = TesteHelper.gerarPagamentoEntidade();

        when(pagamentoRepository.getPagamentoEntityByIdPedido(any(Long.class))).thenReturn(Optional.of(pagamento));
        // Act
        var pagamentoArmazenado = pagamentoRepository.getPagamentoEntityByIdPedido(pagamento.getIdPedido());
        // Assert
        verify(pagamentoRepository, times(1)).getPagamentoEntityByIdPedido(pagamento.getIdPedido());

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

        when(pagamentoRepository.getPagamentoEntityByIdPedido(any(Long.class))).thenReturn(Optional.of(pagamento));
        // Act
        var pagamentoArmazenado = pagamentoRepository.getPagamentoEntityByPagamentoId(pagamento.getPagamentoId());
        // Assert
        verify(pagamentoRepository, times(1)).getPagamentoEntityByPagamentoId(pagamento.getPagamentoId());

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