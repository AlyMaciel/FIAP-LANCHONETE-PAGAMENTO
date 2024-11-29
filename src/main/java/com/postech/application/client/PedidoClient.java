package com.postech.application.client;

import com.postech.domain.enums.EstadoPagamentoEnum;

import java.time.LocalDate;

public interface PedidoClient {

    void enviaEstadoPagamento(Long pedidoId, EstadoPagamentoEnum estadoPagamentoEnum, LocalDate dataPagamento);
}
