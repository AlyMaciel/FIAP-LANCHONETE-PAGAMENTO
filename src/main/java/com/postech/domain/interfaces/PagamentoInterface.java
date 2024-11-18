package com.postech.domain.interfaces;

import com.postech.domain.entities.Pagamento;
import com.postech.infra.dto.request.PedidoRequestDTO;

public interface PagamentoInterface {

    Pagamento criarPagamento(PedidoRequestDTO pedidoDTO);

}
