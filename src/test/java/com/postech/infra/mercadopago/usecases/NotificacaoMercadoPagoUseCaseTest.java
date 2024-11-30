package com.postech.infra.mercadopago.usecases;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.infra.mercadopago.dto.NotificacaoMercadoPagoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    @Test
    void testJsonCreator() throws Exception {
        String jsonString = "{\"action\":\"payment.updated\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{\"id\":\"84010825764\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        NotificacaoMercadoPagoDTO dto = new NotificacaoMercadoPagoDTO(
                jsonNode.get("action").asText(),
                LocalDateTime.parse(jsonNode.get("date_created").asText()),
                jsonNode.get("data")
        );

        assertEquals("payment.updated", dto.getAcao());
        assertEquals(LocalDateTime.of(2024, 7, 29, 22, 0).toLocalDate(), dto.getDataCriada());
        assertEquals("84010825764", dto.getPagamentoId());
    }

    @Test
    void testJsonCreator_NullData() throws Exception {
        String jsonString = "{\"action\":\"payment.updated\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        NotificacaoMercadoPagoDTO dto = new NotificacaoMercadoPagoDTO(
                jsonNode.get("action").asText(),
                LocalDateTime.parse(jsonNode.get("date_created").asText()),
                jsonNode.get("data")
        );

        assertEquals("payment.updated", dto.getAcao());
        assertEquals(LocalDateTime.of(2024, 7, 29, 22, 0).toLocalDate(), dto.getDataCriada());
        assertNull(dto.getPagamentoId());
    }
}