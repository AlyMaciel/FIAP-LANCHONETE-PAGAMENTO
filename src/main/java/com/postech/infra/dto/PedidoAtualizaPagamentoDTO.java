package com.postech.infra.dto;

import com.postech.domain.enums.EstadoPagamentoEnum;

import java.time.LocalDate;

public record PedidoAtualizaPagamentoDTO(EstadoPagamentoEnum estadoPagamentoEnum, LocalDate dataPagamento) {
}
