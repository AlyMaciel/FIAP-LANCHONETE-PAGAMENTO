package com.postech.infra.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriarPagamentoRequestDTOTest {


    @Test
    void testCriarPagamentoRequestDTO() {
        Long idPedido = 1L;
        Double valorTotal = 100.0;
        Long idCliente = 2L;

        CriarPagamentoRequestDTO dto = new CriarPagamentoRequestDTO(idPedido, valorTotal, idCliente);

        assertEquals(idPedido, dto.getIdPedido());
        assertEquals(valorTotal, dto.getValorTotal());
        assertEquals(idCliente, dto.getIdCliente());
    }

    @Test
    void testNoArgsConstructor() {
        CriarPagamentoRequestDTO dto = new CriarPagamentoRequestDTO();

        assertNull(dto.getIdPedido());
        assertNull(dto.getValorTotal());
        assertNull(dto.getIdCliente());
    }

    @Test
    void testSettersAndGetters() {
        CriarPagamentoRequestDTO dto = new CriarPagamentoRequestDTO();
        Long idPedido = 1L;
        Double valorTotal = 100.0;
        Long idCliente = 2L;

        dto.setIdPedido(idPedido);
        dto.setValorTotal(valorTotal);
        dto.setIdCliente(idCliente);

        assertEquals(idPedido, dto.getIdPedido());
        assertEquals(valorTotal, dto.getValorTotal());
        assertEquals(idCliente, dto.getIdCliente());
    }

    @Test
    void testEqualsAndHashCode() {
        CriarPagamentoRequestDTO dto1 = new CriarPagamentoRequestDTO(1L, 100.0, 2L);
        CriarPagamentoRequestDTO dto2 = new CriarPagamentoRequestDTO(1L, 100.0, 2L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        CriarPagamentoRequestDTO dto = new CriarPagamentoRequestDTO(1L, 100.0, 2L);
        String expectedString = "CriarPagamentoRequestDTO(idPedido=1, valorTotal=100.0, idCliente=2)";
        assertEquals(expectedString, dto.toString());
    }



}