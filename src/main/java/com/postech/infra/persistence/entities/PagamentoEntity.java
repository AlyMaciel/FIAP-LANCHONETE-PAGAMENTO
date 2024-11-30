package com.postech.infra.persistence.entities;

import com.google.common.base.Objects;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pagamento")
public class PagamentoEntity {

    @Id
    private ObjectId id;

    @Field("valor")
    private Double valor;

    @Field("estado_pagamento")
    @Enumerated(EnumType.STRING)
    private EstadoPagamentoEnum estadoPagamento;

    @Field("id_pedido")
    private Long idPedido;

    @Field("data_pagamento")
    private LocalDate dataPagamento;

    @Field("data_criacao_pagamento")
    private LocalDate dataCriacaoPagamento;

    @Field("metodo_pagamento")
    @Enumerated(EnumType.STRING)
    private TipoMetodoPagamento metodoPagamento;

    @Field("qr_code")
    private String qrCode;

    @Field("pagamento_id")
    private String pagamentoId;

    @Field("tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private TipoPagamentoEnum tipoPagamento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PagamentoEntity that)) return false;
        return Objects.equal(id, that.id) && Objects.equal(valor, that.valor) && estadoPagamento == that.estadoPagamento && Objects.equal(idPedido, that.idPedido) && Objects.equal(dataPagamento, that.dataPagamento) && Objects.equal(dataCriacaoPagamento, that.dataCriacaoPagamento) && metodoPagamento == that.metodoPagamento && Objects.equal(qrCode, that.qrCode) && Objects.equal(pagamentoId, that.pagamentoId) && tipoPagamento == that.tipoPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, valor, estadoPagamento, idPedido, dataPagamento, dataCriacaoPagamento, metodoPagamento, qrCode, pagamentoId, tipoPagamento);
    }
}

