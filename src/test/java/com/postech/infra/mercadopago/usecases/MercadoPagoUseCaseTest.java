package com.postech.infra.mercadopago.usecases;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.resources.payment.Payment;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.utils.TesteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MercadoPagoUseCaseTest {

    @InjectMocks
    @Spy
    private MercadoPagoUseCase mercadoPagoUseCase;

    @Mock
    private PaymentClient paymentClient;

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
    void testCriarPagamento_Success() throws Exception {
        // Mock do pagamento criado pela API do Mercado Pago
        Payment mockPayment = mock(Payment.class);
        when(mockPayment.getTransactionAmount()).thenReturn(BigDecimal.valueOf(100.0));
        when(mockPayment.getDateCreated()).thenReturn(OffsetDateTime.now());
        when(mockPayment.getId()).thenReturn(12345L);
        doReturn("QRCodeExample").when(mercadoPagoUseCase).getQRCode(any(Payment.class));

        doReturn(paymentClient).when(mercadoPagoUseCase).getPaymentClient();

        // Mock do cliente PaymentClient
        when(paymentClient.create(any(PaymentCreateRequest.class))).thenReturn(mockPayment);

        // Dados do pedido
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        // Chamada do método de teste
        var pagamento = mercadoPagoUseCase.criarPagamento(criarPagamentoRequestDTO);

        // Verificações
        assertNotNull(pagamento);
        assertEquals(EstadoPagamentoEnum.PENDENTE_PAGAMENTO, pagamento.getEstadoPagamento());
        assertEquals(TipoMetodoPagamento.PIX, pagamento.getMetodoPagamento());
        assertEquals(100.0, pagamento.getValor());
        assertEquals(1L, pagamento.getIdPedido());
        assertNotNull(pagamento.getDataCriacaoPagamento());
        assertEquals(TipoPagamentoEnum.MERCADO_PAGO, pagamento.getTipoPagamento());
        assertEquals("QRCodeExample", pagamento.getQrCode());
    }

    @Test
    void testCriarPagamento_Failure() throws Exception {
        doReturn(paymentClient).when(mercadoPagoUseCase).getPaymentClient();


        // Mock para lançar exceção ao criar pagamento
        when(paymentClient.create(any(PaymentCreateRequest.class))).thenThrow(new RuntimeException("Erro no Mercado Pago"));

        // Dados do pedido
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        // Chamada do método e verificação de exceção
        PagamentoException exception = assertThrows(PagamentoException.class, () ->
                mercadoPagoUseCase.criarPagamento(criarPagamentoRequestDTO)
        );

        assertEquals("ERRO_CRIAR_PAGAMENTO", exception.getErro().name());
    }
}
