package com.postech.infra.persistence.entities;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoEntityTest {

    @Test
    void testEqualsAndHashCode() {
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();
        Double valor = 100.0;
        EstadoPagamentoEnum estadoPagamento = EstadoPagamentoEnum.PENDENTE_PAGAMENTO;
        Long idPedido = 12345L;
        LocalDate dataPagamento = LocalDate.now();
        LocalDate dataCriacaoPagamento = LocalDate.now().minusDays(1);
        TipoMetodoPagamento metodoPagamento = TipoMetodoPagamento.PIX;
        String qrCode = "sampleQRCode";
        String pagamentoId = "samplePagamentoId";
        TipoPagamentoEnum tipoPagamento = TipoPagamentoEnum.MERCADO_PAGO;

        PagamentoEntity pagamentoEntity1 = new PagamentoEntity(id1, valor, estadoPagamento, idPedido, dataPagamento, dataCriacaoPagamento, metodoPagamento, qrCode, pagamentoId, tipoPagamento);
        PagamentoEntity pagamentoEntity2 = new PagamentoEntity(id1, valor, estadoPagamento, idPedido, dataPagamento, dataCriacaoPagamento, metodoPagamento, qrCode, pagamentoId, tipoPagamento);
        PagamentoEntity pagamentoEntity3 = new PagamentoEntity(id2, valor, estadoPagamento, idPedido, dataPagamento, dataCriacaoPagamento, metodoPagamento, qrCode, pagamentoId, tipoPagamento);

        // Test equals
        assertEquals(pagamentoEntity1, pagamentoEntity2);
        assertNotEquals(pagamentoEntity1, pagamentoEntity3);

        assertNotEquals(null, pagamentoEntity3);


        // Test hashCode
        assertEquals(pagamentoEntity1.hashCode(), pagamentoEntity2.hashCode());
        assertNotEquals(pagamentoEntity1.hashCode(), pagamentoEntity3.hashCode());
    }

    @Test
    void testToString() {
        ObjectId id = new ObjectId();
        PagamentoEntity pagamentoEntity = new PagamentoEntity(id, 100.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 12345L, LocalDate.now(), LocalDate.now().minusDays(1), TipoMetodoPagamento.PIX, "sampleQRCode", "samplePagamentoId", TipoPagamentoEnum.MERCADO_PAGO);

        String expectedString = "PagamentoEntity(id=" + id.toString() + ", valor=100.0, estadoPagamento=PENDENTE_PAGAMENTO, idPedido=12345, dataPagamento=" + LocalDate.now().toString() + ", dataCriacaoPagamento=" + LocalDate.now().minusDays(1).toString() + ", metodoPagamento=PIX, qrCode=sampleQRCode, pagamentoId=samplePagamentoId, tipoPagamento=MERCADO_PAGO)";
        assertEquals(expectedString, pagamentoEntity.toString());
    }

    @Test
    void testGettersAndSetters() {
        ObjectId id = new ObjectId();
        Double valor = 100.0;
        EstadoPagamentoEnum estadoPagamento = EstadoPagamentoEnum.PENDENTE_PAGAMENTO;
        Long idPedido = 12345L;
        LocalDate dataPagamento = LocalDate.now();
        LocalDate dataCriacaoPagamento = LocalDate.now().minusDays(1);
        TipoMetodoPagamento metodoPagamento = TipoMetodoPagamento.PIX;
        String qrCode = "sampleQRCode";
        String pagamentoId = "samplePagamentoId";
        TipoPagamentoEnum tipoPagamento = TipoPagamentoEnum.MERCADO_PAGO;

        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(id);
        pagamentoEntity.setValor(valor);
        pagamentoEntity.setEstadoPagamento(estadoPagamento);
        pagamentoEntity.setIdPedido(idPedido);
        pagamentoEntity.setDataPagamento(dataPagamento);
        pagamentoEntity.setDataCriacaoPagamento(dataCriacaoPagamento);
        pagamentoEntity.setMetodoPagamento(metodoPagamento);
        pagamentoEntity.setQrCode(qrCode);
        pagamentoEntity.setPagamentoId(pagamentoId);
        pagamentoEntity.setTipoPagamento(tipoPagamento);

        assertEquals(id, pagamentoEntity.getId());
        assertEquals(valor, pagamentoEntity.getValor());
        assertEquals(estadoPagamento, pagamentoEntity.getEstadoPagamento());
        assertEquals(idPedido, pagamentoEntity.getIdPedido());
        assertEquals(dataPagamento, pagamentoEntity.getDataPagamento());
        assertEquals(dataCriacaoPagamento, pagamentoEntity.getDataCriacaoPagamento());
        assertEquals(metodoPagamento, pagamentoEntity.getMetodoPagamento());
        assertEquals(qrCode, pagamentoEntity.getQrCode());
        assertEquals(pagamentoId, pagamentoEntity.getPagamentoId());
        assertEquals(tipoPagamento, pagamentoEntity.getTipoPagamento());
    }

    @Test
    void testPagamentoEntity() {
        ObjectId id = new ObjectId();
        Double valor = 100.0;
        EstadoPagamentoEnum estadoPagamento = EstadoPagamentoEnum.PENDENTE_PAGAMENTO;
        Long idPedido = 12345L;
        LocalDate dataPagamento = LocalDate.now();
        LocalDate dataCriacaoPagamento = LocalDate.now().minusDays(1);
        TipoMetodoPagamento metodoPagamento = TipoMetodoPagamento.PIX;
        String qrCode = "sampleQRCode";
        String pagamentoId = "samplePagamentoId";
        TipoPagamentoEnum tipoPagamento = TipoPagamentoEnum.MERCADO_PAGO;

        PagamentoEntity pagamentoEntity = new PagamentoEntity(id, valor, estadoPagamento, idPedido, dataPagamento, dataCriacaoPagamento, metodoPagamento, qrCode, pagamentoId, tipoPagamento);

        assertNotNull(pagamentoEntity);
        assertEquals(id, pagamentoEntity.getId());
        assertEquals(valor, pagamentoEntity.getValor());
        assertEquals(estadoPagamento, pagamentoEntity.getEstadoPagamento());
        assertEquals(idPedido, pagamentoEntity.getIdPedido());
        assertEquals(dataPagamento, pagamentoEntity.getDataPagamento());
        assertEquals(dataCriacaoPagamento, pagamentoEntity.getDataCriacaoPagamento());
        assertEquals(metodoPagamento, pagamentoEntity.getMetodoPagamento());
        assertEquals(qrCode, pagamentoEntity.getQrCode());
        assertEquals(pagamentoId, pagamentoEntity.getPagamentoId());
        assertEquals(tipoPagamento, pagamentoEntity.getTipoPagamento());
    }

    @Test
    void testNoArgsConstructor() {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        assertNotNull(pagamentoEntity);
    }

}