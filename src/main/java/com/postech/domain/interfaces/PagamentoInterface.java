package com.postech.domain.interfaces;

import com.postech.domain.entities.Pagamento;
import com.postech.infra.dto.request.CriarPagamentoRequestDTO;

public interface PagamentoInterface {

    Pagamento criarPagamento(CriarPagamentoRequestDTO criarPagamentoRequestDTO);

}
