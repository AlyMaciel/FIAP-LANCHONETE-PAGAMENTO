package com.postech.infra.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.infra.dto.PedidoAtualizaPagamentoDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.time.LocalDate;



class PedidoClienteImplTest {


    @InjectMocks
    private PedidoClienteImpl pedidoCliente;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void init() {
        pedidoCliente = new PedidoClienteImpl(String.format("http://localhost:%s", mockWebServer.getPort()));
    }

    @Test
    void testConsultarStatusPedido() {
        var pedidoAtualizaPagamentoDTO = new PedidoAtualizaPagamentoDTO(EstadoPagamentoEnum.PAGO, LocalDate.now());


        mockWebServer.enqueue(new MockResponse()
                .setBody(asJsonString(pedidoAtualizaPagamentoDTO))
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        pedidoCliente.enviaEstadoPagamento(1L, EstadoPagamentoEnum.PAGO, LocalDate.now());
    }


    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper  = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}