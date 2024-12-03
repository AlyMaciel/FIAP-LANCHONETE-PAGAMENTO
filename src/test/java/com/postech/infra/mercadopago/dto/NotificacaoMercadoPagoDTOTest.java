package com.postech.infra.mercadopago.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoMercadoPagoDTOTest {

    @Test
    void testJsonCreatorConstructor() throws Exception {
        String action = "payment.created";
        LocalDateTime dateCreated = LocalDateTime.of(2023, 10, 1, 12, 0);
        String jsonData = "{\"id\": \"12345\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode dataNode = mapper.readTree(jsonData);

        NotificacaoMercadoPagoDTO dto = new NotificacaoMercadoPagoDTO(action, dateCreated, dataNode);

        assertEquals(action, dto.getAcao());
        assertEquals(dateCreated.toLocalDate(), dto.getDataCriada());
        assertEquals("12345", dto.getPagamentoId());
    }

    @Test
    void testNoArgsConstructor() {
        NotificacaoMercadoPagoDTO dto = new NotificacaoMercadoPagoDTO();
        assertNull(dto.getAcao());
        assertNull(dto.getDataCriada());
        assertNull(dto.getPagamentoId());
    }

    @Test
    void testAllArgsConstructor() {
        String action = "payment.created";
        LocalDate dateCreated = LocalDate.of(2023, 10, 1);
        String paymentId = "12345";

        NotificacaoMercadoPagoDTO dto = new NotificacaoMercadoPagoDTO(action, dateCreated, paymentId);

        assertEquals(action, dto.getAcao());
        assertEquals(dateCreated, dto.getDataCriada());
        assertEquals(paymentId, dto.getPagamentoId());
    }

    @Test
    void testEqualsAndHashCode() throws Exception {
        String action = "payment.created";
        LocalDateTime dateCreated = LocalDateTime.of(2023, 10, 1, 12, 0);
        String jsonData = "{\"id\": \"12345\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode dataNode = mapper.readTree(jsonData);

        NotificacaoMercadoPagoDTO dto1 = new NotificacaoMercadoPagoDTO(action, dateCreated, dataNode);
        NotificacaoMercadoPagoDTO dto2 = new NotificacaoMercadoPagoDTO(action, dateCreated, dataNode);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() throws Exception {
        String action = "payment.created";
        LocalDateTime dateCreated = LocalDateTime.of(2023, 10, 1, 12, 0);
        String jsonData = "{\"id\": \"12345\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode dataNode = mapper.readTree(jsonData);

        NotificacaoMercadoPagoDTO dto = new NotificacaoMercadoPagoDTO(action, dateCreated, dataNode);

        String expectedString = "NotificacaoMercadoPagoDTO(acao=payment.created, dataCriada=2023-10-01, pagamentoId=12345)";
        assertEquals(expectedString, dto.toString());
    }

}