package com.postech.infra.controller;

import com.postech.application.usecases.PagamentoUseCases;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.infra.dto.request.CriarPagamentoRequestDTO;
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
    public ResponseEntity<Object> criarPagamento(CriarPagamentoRequestDTO criarPagamentoRequestDTO) {
        Pagamento pagamento = useCases.criarPagamentoPix(criarPagamentoRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraResponseDTO(pagamento));
    }

    @Override
    public ResponseEntity<Object> getEstadoPagamento(Long idPedido) {

        EstadoPagamentoEnum estadoPagamento = useCases.getStatusPagamento(idPedido);

        return ResponseEntity.ok().body(estadoPagamento);
    }
}
