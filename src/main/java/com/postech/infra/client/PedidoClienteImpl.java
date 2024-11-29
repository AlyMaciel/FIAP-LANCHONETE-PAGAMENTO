package com.postech.infra.client;

import com.postech.application.client.PedidoClient;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.infra.dto.PedidoAtualizaPagamentoDTO;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

public class PedidoClienteImpl implements PedidoClient {

    private final String pedidoUrl;

    public PedidoClienteImpl(String pedidoUrl) {
        this.pedidoUrl = pedidoUrl;
    }

    @Override
    public void enviaEstadoPagamento(Long pedidoId, EstadoPagamentoEnum estadoPagamentoEnum, LocalDate dataPagamento) {
         WebClient.builder()
                .baseUrl(pedidoUrl)
                .build()
                .post()
                .uri("/{pedidoId}", pedidoId)
                .bodyValue(new PedidoAtualizaPagamentoDTO(estadoPagamentoEnum, dataPagamento))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
