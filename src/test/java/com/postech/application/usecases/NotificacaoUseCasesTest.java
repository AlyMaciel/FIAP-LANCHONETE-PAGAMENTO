package com.postech.application.usecases;

import com.postech.domain.interfaces.NotificacaoExternoInterface;
import com.postech.utils.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class NotificacaoUseCasesTest {

    @Mock
    private NotificacaoExternoInterface notificacaoExternoInterface;

    @Mock
    private PagamentoUseCases pagamentoUseCases;

    @InjectMocks
    private NotificacaoUseCases notificacaoUseCases;

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
    void deveAtualizarStatusPagamento(){
        // Arrange
        var json = "json";

        var notificacaoPagamento = PagamentoHelper.gerarNotificacaoPagamentoPago();

        when(notificacaoExternoInterface.geraNotificaoPagamento(json)).thenReturn(notificacaoPagamento);

        notificacaoUseCases.atualizaNotificacaoPagamento(json);
        // Assert
        verify(pagamentoUseCases, times(1)).atualizaEstadoPagamento(notificacaoPagamento);
    }

    @Test
    void deveAtualizarStatusPagamento_NaoAtualiza_PagamentoPendente(){
        // Arrange
        var json = "json";

        var notificacaoPagamento = PagamentoHelper.gerarNotificacaoPagamentoPendentePagamento();

        when(notificacaoExternoInterface.geraNotificaoPagamento(json)).thenReturn(notificacaoPagamento);

        notificacaoUseCases.atualizaNotificacaoPagamento(json);
        // Assert
        verify(pagamentoUseCases, never()).atualizaEstadoPagamento(notificacaoPagamento);
    }


}