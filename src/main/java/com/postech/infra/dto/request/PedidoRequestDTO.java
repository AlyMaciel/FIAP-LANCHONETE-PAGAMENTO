package com.postech.infra.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoRequestDTO {

    Long id;

    BigDecimal valorTotal;

    ClienteRequestDTO cliente;
}
