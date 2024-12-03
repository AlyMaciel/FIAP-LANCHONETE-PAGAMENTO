package com.postech.infra.mercadopago.usecases;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.*;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.domain.interfaces.PagamentoInterface;
import com.postech.infra.dto.request.CriarPagamentoRequestDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class MercadoPagoUseCase implements PagamentoInterface {

    private static final TipoPagamentoEnum TIPO_PAGAMENTO = TipoPagamentoEnum.MERCADO_PAGO;

    @Override
    public Pagamento criarPagamento(CriarPagamentoRequestDTO criarPagamentoRequestDTO) {
        try{
            PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                    .transactionAmount(BigDecimal.valueOf(criarPagamentoRequestDTO.getValorTotal()))
                    .description("Pagamento do pedido " + criarPagamentoRequestDTO.getIdPedido())
                    .paymentMethodId("pix")
                    .payer(criaPagador())
                    .dateOfExpiration(OffsetDateTime.now().plusMinutes(15))
                    .notificationUrl(null)
                    .build();

            PaymentClient paymentClient = getPaymentClient();

            Payment payment = paymentClient.create(paymentCreateRequest);

            return new Pagamento(null, payment.getTransactionAmount().doubleValue(),
                    EstadoPagamentoEnum.PENDENTE_PAGAMENTO, criarPagamentoRequestDTO.getIdPedido(), null, payment.getDateCreated().toLocalDate(),
                    TipoMetodoPagamento.PIX, getQRCode(payment), TIPO_PAGAMENTO,
                    payment.getId().toString());
        } catch (Exception e){
            throw new PagamentoException(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO);
        }
    }

    protected PaymentClient getPaymentClient() {
        return new PaymentClient();
    }

    protected String getQRCode(Payment payment) {
        return payment.getPointOfInteraction().getTransactionData().getQrCode();
    }


    private PaymentPayerRequest criaPagador() {
        return PaymentPayerRequest.builder()
                .email("teste@gmail.com")
                .firstName("Test")
                .lastName("teste")
                .identification(IdentificationRequest.builder().type("CPF").number("19119119100").build())
                .build();
    }
}
