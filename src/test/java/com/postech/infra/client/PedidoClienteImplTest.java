package com.postech.infra.client;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.infra.dto.PedidoAtualizaPagamentoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoClienteImplTest {


    private PedidoClienteImpl pedidoCliente;
    private WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    private WebClient.RequestBodySpec requestBodySpec;
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        webClientBuilder = mock(WebClient.Builder.class);
        webClient = mock(WebClient.class);
        requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        requestBodySpec = mock(WebClient.RequestBodySpec.class);
        requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), anyLong())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(PedidoAtualizaPagamentoDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        pedidoCliente = new PedidoClienteImpl("http://localhost:8080/lanchonete/v1/pedidos");
    }

    @Test
    void enviaEstadoPagamento_WebClientResponseException() {
        when(responseSpec.toBodilessEntity()).thenThrow(WebClientRequestException.class);

        assertThrows(WebClientResponseException.class, () ->
                pedidoCliente.enviaEstadoPagamento(1L, EstadoPagamentoEnum.PAGO, LocalDate.now())
        );

    }

}