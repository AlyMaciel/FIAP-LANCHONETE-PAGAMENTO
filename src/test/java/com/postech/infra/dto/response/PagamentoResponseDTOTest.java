package com.postech.infra.dto.response;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoResponseDTOTest {


    @Test
    void testEqualsAndHashCode() {
        PagamentoResponseDTO dto1 = new PagamentoResponseDTO(100.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, LocalDate.now(), LocalDate.now().minusDays(1), TipoPagamentoEnum.MERCADO_PAGO, TipoMetodoPagamento.PIX, "sampleQRCode", "12345", 67890L);
        PagamentoResponseDTO dto2 = new PagamentoResponseDTO(100.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, LocalDate.now(), LocalDate.now().minusDays(1), TipoPagamentoEnum.MERCADO_PAGO, TipoMetodoPagamento.PIX, "sampleQRCode", "12345", 67890L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        PagamentoResponseDTO dto = new PagamentoResponseDTO(100.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, LocalDate.now(), LocalDate.now().minusDays(1), TipoPagamentoEnum.MERCADO_PAGO, TipoMetodoPagamento.PIX, "sampleQRCode", "12345", 67890L);

        String expectedString = "PagamentoResponseDTO(valor=100.0, estadoPagamento=PENDENTE_PAGAMENTO, dataPagamento=" + LocalDate.now().toString() + ", dataCriacaoPagamento=" + LocalDate.now().minusDays(1).toString() + ", tipoPagamento=MERCADO_PAGO, metodoPagamento=PIX, qrCode=sampleQRCode, pagamentoId=12345, idPedido=67890)";
        assertEquals(expectedString, dto.toString());
    }

    @Test
    void testPagamentoResponseDTO() {
        Double valor = 100.0;
        EstadoPagamentoEnum estadoPagamento = EstadoPagamentoEnum.PENDENTE_PAGAMENTO;
        LocalDate dataPagamento = LocalDate.now();
        LocalDate dataCriacaoPagamento = LocalDate.now().minusDays(1);
        TipoPagamentoEnum tipoPagamento = TipoPagamentoEnum.MERCADO_PAGO;
        TipoMetodoPagamento metodoPagamento = TipoMetodoPagamento.PIX;
        String qrCode = "sampleQRCode";
        String pagamentoId = "12345";
        Long idPedido = 67890L;

        PagamentoResponseDTO dto = new PagamentoResponseDTO(valor, estadoPagamento, dataPagamento, dataCriacaoPagamento, tipoPagamento, metodoPagamento, qrCode, pagamentoId, idPedido);

        assertNotNull(dto);
        assertEquals(valor, dto.getValor());
        assertEquals(estadoPagamento, dto.getEstadoPagamento());
        assertEquals(dataPagamento, dto.getDataPagamento());
        assertEquals(dataCriacaoPagamento, dto.getDataCriacaoPagamento());
        assertEquals(tipoPagamento, dto.getTipoPagamento());
        assertEquals(metodoPagamento, dto.getMetodoPagamento());
        assertEquals(qrCode, dto.getQrCode());
        assertEquals(pagamentoId, dto.getPagamentoId());
        assertEquals(idPedido, dto.getIdPedido());
    }

}