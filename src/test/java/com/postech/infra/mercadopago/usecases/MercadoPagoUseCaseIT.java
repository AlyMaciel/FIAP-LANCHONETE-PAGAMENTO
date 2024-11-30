package com.postech.infra.mercadopago.usecases;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.utils.TesteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class MercadoPagoUseCaseIT {

    private MercadoPagoUseCase mercadoPagoUseCase;

    @BeforeEach
    void setUp() {
        mercadoPagoUseCase = new MercadoPagoUseCase();
    }

    @Test
    void testCriarPagamento_ComIntegracaoReal() {
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();

        // Chamada do método
        var pagamento = mercadoPagoUseCase.criarPagamento(criarPagamentoRequestDTO);

        // Validações
        assertNotNull(pagamento);
        assertEquals(EstadoPagamentoEnum.PENDENTE_PAGAMENTO, pagamento.getEstadoPagamento());
        assertNotNull(pagamento.getQrCode());
    }



}
