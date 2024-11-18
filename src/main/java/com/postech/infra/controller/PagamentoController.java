package com.postech.infra.controller;

import com.postech.application.usecases.PagamentoUseCases;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.infra.dto.request.PagamentoRequestDTO;
import com.postech.infra.mappers.PagamentoMapper;
import com.postech.infra.resource.PagamentoResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PagamentoController implements PagamentoResource {

    private final PagamentoUseCases useCases;

    private final PagamentoMapper mapper;

    public PagamentoController(PagamentoUseCases useCases, PagamentoMapper mapper) {
        this.useCases = useCases;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Object> criarPagamento(PagamentoRequestDTO pagamentoRequestDTO) {
        Pagamento pagamento = useCases.criarPagamentoPix(pagamentoRequestDTO.getPedidoDTO());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraResponseDTO(pagamento));
    }

    @Override
    public ResponseEntity<Object> getEstadoPagamento(PagamentoRequestDTO pagamentoRequestDTO) {

        EstadoPagamentoEnum estadoPagamento = useCases.getStatusPagamento(pagamentoRequestDTO.getPedidoDTO().getId());

        return ResponseEntity.ok().body("O estado do pagamento informado é: " + estadoPagamento);
    }
}
