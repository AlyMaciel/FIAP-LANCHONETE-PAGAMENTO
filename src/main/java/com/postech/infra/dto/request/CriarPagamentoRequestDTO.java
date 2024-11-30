package com.postech.infra.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CriarPagamentoRequestDTO {

    Long idPedido;

    Double valorTotal;

    Long idCliente;
}
