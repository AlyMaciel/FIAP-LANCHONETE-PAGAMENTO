package com.postech.infra.mercadopago.usecases;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.infra.dto.request.ClienteRequestDTO;
import com.postech.infra.dto.request.PedidoRequestDTO;
import com.postech.utils.PagamentoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class MercadoPagoUseCaseIT {

    private MercadoPagoUseCase mercadoPagoUseCase;

    @BeforeEach
    void setUp() {
        mercadoPagoUseCase = new MercadoPagoUseCase();
    }

    @Test
    void testCriarPagamento_ComIntegracaoReal() {
        var pedidoRequestDTO = PagamentoHelper.gerarPedidoRequest();

        // Chamada do método
        var pagamento = mercadoPagoUseCase.criarPagamento(pedidoRequestDTO);

        // Validações
        assertNotNull(pagamento);
        assertEquals(EstadoPagamentoEnum.PENDENTE_PAGAMENTO, pagamento.getEstadoPagamento());
        assertNotNull(pagamento.getQrCode());
    }



}
