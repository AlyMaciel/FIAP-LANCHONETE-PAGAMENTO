package com.postech.infra.dto;

import com.postech.domain.enums.EstadoPagamentoEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoAtualizaPagamentoDTOTest {

    @Test
    void pedidoAtualizaPagamentoDTOWithValidData() {
        PedidoAtualizaPagamentoDTO dto = new PedidoAtualizaPagamentoDTO(EstadoPagamentoEnum.PAGO, LocalDate.now());
        assertNotNull(dto);
        assertEquals(EstadoPagamentoEnum.PAGO, dto.estadoPagamentoEnum());
        assertEquals(LocalDate.now(), dto.dataPagamento());
    }

}