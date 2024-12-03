package com.postech.config;

import com.postech.application.client.PedidoClient;
import com.postech.application.gateways.RepositorioDePagamentoGateway;
import com.postech.application.usecases.PagamentoUseCases;
import com.postech.domain.interfaces.PagamentoInterface;
import com.postech.infra.gateways.RepositorioDePagamentoImpl;
import com.postech.infra.mappers.PagamentoMapper;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoConfigurationTest {

    @InjectMocks
    private PagamentoConfiguration pagamentoConfiguration;

    @Mock
    private RepositorioDePagamentoGateway repositorioDePagamentoGateway;

    @Mock
    private PagamentoInterface pagamentoInterface;

    @Mock
    private PedidoClient pedidoClient;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoMapper pagamentoMapper;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testPagamentoUseCasesBean() {
        PagamentoUseCases pagamentoUseCases = pagamentoConfiguration.pagamentoUseCases(repositorioDePagamentoGateway, pagamentoInterface, pedidoClient);
        assertNotNull(pagamentoUseCases);
    }

    @Test
    void testRepositorioDePagamentoImplBean() {
        RepositorioDePagamentoImpl repositorioDePagamentoImpl = pagamentoConfiguration.repositorioDePagamentoGateway(pagamentoRepository, pagamentoMapper);
        assertNotNull(repositorioDePagamentoImpl);
    }

    @Test
    void testPagamentoInterfaceBean() {
        assertNotNull( pagamentoConfiguration.pagamentoInterface());
    }

}