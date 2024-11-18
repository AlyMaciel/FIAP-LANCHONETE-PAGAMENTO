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
import com.postech.infra.dto.request.PedidoRequestDTO;

import java.time.OffsetDateTime;

public class MercadoPagoUseCase implements PagamentoInterface {

    private static final TipoPagamentoEnum TIPO_PAGAMENTO = TipoPagamentoEnum.MERCADO_PAGO;

    public MercadoPagoUseCase() {
    }

    @Override
    public Pagamento criarPagamento(PedidoRequestDTO pedidoDTO) {
        try{
            PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                    .transactionAmount(pedidoDTO.getValorTotal())
                    .description("Pagamento do pedido " + pedidoDTO.getId())
                    .paymentMethodId("pix")
                    .payer(criaPagador(pedidoDTO))
                    .dateOfExpiration(OffsetDateTime.now().plusMinutes(15))
                    .notificationUrl(null)
                    .build();

            PaymentClient paymentClient = new PaymentClient();

            Payment payment = paymentClient.create(paymentCreateRequest);

            return new Pagamento(null, payment.getTransactionAmount().doubleValue(),
                    EstadoPagamentoEnum.PENDENTE_PAGAMENTO, pedidoDTO.getId(), null, payment.getDateCreated().toLocalDate(),
                    TipoMetodoPagamento.PIX, payment.getPointOfInteraction().getTransactionData().getQrCode(), TIPO_PAGAMENTO,
                    payment.getId().toString());
        } catch (Exception e){
            throw new PagamentoException(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO);
        }
    }


    private PaymentPayerRequest criaPagador(PedidoRequestDTO pedido) {
        return PaymentPayerRequest.builder()
                .email(pedido.getCliente().getEmail())
                .firstName(pedido.getCliente().getNome())
                .lastName("teste")
                .email(pedido.getCliente().getEmail())
                .identification(IdentificationRequest.builder().type("CPF").number(pedido.getCliente().getCpf()).build())
                .build();
    }
}
