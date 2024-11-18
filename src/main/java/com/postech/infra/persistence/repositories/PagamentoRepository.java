package com.postech.infra.persistence.repositories;

import com.postech.infra.persistence.entities.PagamentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PagamentoRepository extends MongoRepository<PagamentoEntity, Long> {


    Optional<PagamentoEntity> getPagamentoEntityByIdPedido(Long id);
    Optional<PagamentoEntity> getPagamentoEntityByPagamentoId(String id);
}
