package com.postech.application.utils;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.infra.persistence.entities.PagamentoEntity;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoPagamentoTest {

    @Test
    void testEqualsAndHashCode() {
        // Criando objetos de teste
        PagamentoEntity pagamento1 = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        PagamentoEntity pagamento2 = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        PagamentoEntity pagamento3 = new PagamentoEntity(
                new ObjectId("1e2e71b5e82e3a6c055e93d5"),
                200.0,
                EstadoPagamentoEnum.PENDENTE_PAGAMENTO,
                2L,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR456",
                "PG002",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        // Verificando igualdade
        assertEquals(pagamento1, pagamento2, "Os objetos devem ser iguais");
        assertNotEquals(pagamento1, pagamento3, "Os objetos não devem ser iguais");

        // Verificando hashCode
        assertEquals(pagamento1.hashCode(), pagamento2.hashCode(), "Os hashCodes devem ser iguais para objetos iguais");
        assertNotEquals(pagamento1.hashCode(), pagamento3.hashCode(), "Os hashCodes devem ser diferentes para objetos diferentes");

        // Verificando comportamento com null
        assertNotEquals(null, pagamento1, "Um objeto nunca deve ser igual a null");

        // Verificando comportamento com outro tipo de objeto
        assertNotEquals("String", pagamento1, "Um objeto nunca deve ser igual a um tipo diferente");
    }

    @Test
    void testReflexiveProperty() {
        PagamentoEntity pagamento = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        assertEquals(pagamento, pagamento, "Um objeto deve ser igual a si mesmo");
    }

    @Test
    void testSymmetricProperty() {
        PagamentoEntity pagamento1 = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        PagamentoEntity pagamento2 = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        assertTrue(pagamento1.equals(pagamento2) && pagamento2.equals(pagamento1), "A igualdade deve ser simétrica");
    }

    @Test
    void testTransitiveProperty() {
        PagamentoEntity pagamento1 = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        PagamentoEntity pagamento2 = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        PagamentoEntity pagamento3 = new PagamentoEntity(
                new ObjectId("655e71b5e82e3a6c055e93d5"),
                100.0,
                EstadoPagamentoEnum.PAGO,
                1L,
                LocalDate.of(2024, 11, 30),
                LocalDate.of(2024, 11, 29),
                TipoMetodoPagamento.PIX,
                "QR123",
                "PG001",
                TipoPagamentoEnum.MERCADO_PAGO
        );

        assertTrue(
                pagamento1.equals(pagamento2) && pagamento2.equals(pagamento3) && pagamento1.equals(pagamento3),
                "A igualdade deve ser transitiva"
        );
    }

    @Test
    void testToString() {
        NotificacaoPagamento notificacao = new NotificacaoPagamento(EstadoPagamentoEnum.PENDENTE_PAGAMENTO, LocalDate.now(), "12345");

        String expectedString = "NotificacaoPagamento(estadoPagamento=PENDENTE_PAGAMENTO, dataAttPagamento=" + LocalDate.now().toString() + ", pagamentoId=12345)";
        assertEquals(expectedString, notificacao.toString());
    }


    @Test
    void testNoArgsConstructor() {
        NotificacaoPagamento notificacaoPagamento = new NotificacaoPagamento();
        assertNotNull(notificacaoPagamento);
        assertNull(notificacaoPagamento.getEstadoPagamento());
        assertNull(notificacaoPagamento.getDataAttPagamento());
        assertNull(notificacaoPagamento.getPagamentoId());
    }

    @Test
    void testNotificacaoPagamento() {
        EstadoPagamentoEnum estadoPagamento = EstadoPagamentoEnum.PAGO;
        LocalDate dataAttPagamento = LocalDate.now();
        String pagamentoId = "12345";

        NotificacaoPagamento notificacaoPagamento = new NotificacaoPagamento(estadoPagamento, dataAttPagamento, pagamentoId);

        assertNotNull(notificacaoPagamento);
        assertEquals(estadoPagamento, notificacaoPagamento.getEstadoPagamento());
        assertEquals(dataAttPagamento, notificacaoPagamento.getDataAttPagamento());
        assertEquals(pagamentoId, notificacaoPagamento.getPagamentoId());
    }

}