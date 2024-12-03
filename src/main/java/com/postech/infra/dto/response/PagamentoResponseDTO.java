package com.postech.infra.dto.response;


import com.google.common.base.Objects;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagamentoResponseDTO {

    private Double valor;

    private EstadoPagamentoEnum estadoPagamento;

    private LocalDate dataPagamento;

    private LocalDate dataCriacaoPagamento;

    private TipoPagamentoEnum tipoPagamento;

    private TipoMetodoPagamento metodoPagamento;

    private String qrCode;

    private String pagamentoId;

    private Long idPedido;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PagamentoResponseDTO that)) return false;
        return Objects.equal(valor, that.valor) && estadoPagamento == that.estadoPagamento && Objects.equal(dataPagamento, that.dataPagamento) && Objects.equal(dataCriacaoPagamento, that.dataCriacaoPagamento) && tipoPagamento == that.tipoPagamento && metodoPagamento == that.metodoPagamento && Objects.equal(qrCode, that.qrCode) && Objects.equal(pagamentoId, that.pagamentoId) && Objects.equal(idPedido, that.idPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valor, estadoPagamento, dataPagamento, dataCriacaoPagamento, tipoPagamento, metodoPagamento, qrCode, pagamentoId, idPedido);
    }
}
