package com.postech.infra.mercadopago.usecases;

import com.postech.domain.enums.EstadoPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoMercadoPagoUseCaseTest {

    private NotificacaoMercadoPagoUseCase notificacaoMercadoPagoUseCase;

    @BeforeEach
    void setUp() {
        notificacaoMercadoPagoUseCase = new NotificacaoMercadoPagoUseCase();
    }

    @Test
    void testGeraNotificaoPagamento_PaymentUpdated()  {
        var jsonString = "{\"action\":\"payment.updated\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{\"id\":\"84010825764\"}}";

        var notificacaoPagamento = notificacaoMercadoPagoUseCase.geraNotificaoPagamento(jsonString);

        assertNotNull(notificacaoPagamento);
        assertEquals(EstadoPagamentoEnum.PAGO, notificacaoPagamento.getEstadoPagamento());
        assertEquals(LocalDate.of(2024, Month.JULY, 29), notificacaoPagamento.getDataAttPagamento());
        assertEquals("84010825764", notificacaoPagamento.getPagamentoId());
    }

    @Test
    void testGeraNotificaoPagamento_PendingPayment() {
        var jsonString = "{\"action\":\"payment.created\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{\"id\":\"84010825764\"}}";

        var notificacaoPagamento = notificacaoMercadoPagoUseCase.geraNotificaoPagamento(jsonString);

        assertNotNull(notificacaoPagamento);
        assertEquals(EstadoPagamentoEnum.PENDENTE_PAGAMENTO, notificacaoPagamento.getEstadoPagamento());
        assertEquals(LocalDate.of(2024, Month.JULY, 29), notificacaoPagamento.getDataAttPagamento());
        assertEquals("84010825764", notificacaoPagamento.getPagamentoId());
    }

    @Test
    void testGeraNotificaoPagamento_JsonProcessingException()  {
        var jsonNotificacao = "{\"acao\":\"invalid\",\"dataCriada\":\"2023-10-01T10:00:00Z\",\"pagamentoId\":\"123\"}";

        var notificacaoPagamento = notificacaoMercadoPagoUseCase.geraNotificaoPagamento(jsonNotificacao);

        assertNotNull(notificacaoPagamento);
        assertEquals(EstadoPagamentoEnum.PENDENTE_PAGAMENTO, notificacaoPagamento.getEstadoPagamento());
        assertNull(notificacaoPagamento.getDataAttPagamento());
        assertNull(notificacaoPagamento.getPagamentoId());


    }
}