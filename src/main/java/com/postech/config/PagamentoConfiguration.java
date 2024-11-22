package com.postech.config;

import com.postech.application.gateways.RepositorioDePagamentoGateway;
import com.postech.application.usecases.PagamentoUseCases;
import com.postech.domain.interfaces.PagamentoInterface;
import com.postech.infra.gateways.RepositorioDePagamentoImpl;
import com.postech.infra.mappers.PagamentoMapper;
import com.postech.infra.mercadopago.usecases.MercadoPagoUseCase;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

@Configuration
public class PagamentoConfiguration {

    @Bean
    PagamentoUseCases pagamentoUseCases(RepositorioDePagamentoGateway repositorio, PagamentoInterface pagamentoExternoInterface) {
        return new PagamentoUseCases(repositorio, pagamentoExternoInterface);
    }

    @Bean
    RepositorioDePagamentoImpl repositorioDePagamentoGateway(PagamentoRepository repository, PagamentoMapper mapper) {
        return new RepositorioDePagamentoImpl(repository, mapper);
    }

    @Bean
    PagamentoInterface pagamentoInterface(){
        return new MercadoPagoUseCase();
    }


}