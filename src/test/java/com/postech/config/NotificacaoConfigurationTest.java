package com.postech.config;

import com.postech.application.usecases.NotificacaoUseCases;
import com.postech.application.usecases.PagamentoUseCases;
import com.postech.domain.interfaces.NotificacaoExternoInterface;
import com.postech.infra.mercadopago.usecases.NotificacaoMercadoPagoUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoConfigurationTest {


    @InjectMocks
    private NotificacaoConfiguration notificacaoConfiguration;

    @Mock
    private PagamentoUseCases pagamentoUseCases;

    @Mock
    private NotificacaoExternoInterface notificacaoInterface;

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
    void testNotificacaoUseCasesBean() {
        NotificacaoUseCases notificacaoUseCases = notificacaoConfiguration.notificacaoUseCases(pagamentoUseCases, notificacaoInterface);
        assertNotNull(notificacaoUseCases);
    }

    @Test
    void testPagamentoExternoBean() {
        NotificacaoExternoInterface notificacaoExterno = notificacaoConfiguration.pagamentoExterno();
        assertNotNull(notificacaoExterno);
        assertInstanceOf(NotificacaoMercadoPagoUseCase.class, notificacaoExterno);
    }

}