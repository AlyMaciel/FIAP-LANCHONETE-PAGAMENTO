package com.postech.utils;

import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.infra.persistence.entities.PagamentoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public abstract class PagamentoHelper {

    public static PagamentoEntity gerarPagamentoEntidade() {
        return new PagamentoEntity(new ObjectId(), 100.0, EstadoPagamentoEnum.PAGO, 213L, LocalDate.now(), LocalDate.now(), TipoMetodoPagamento.PIX, "123456", "123456", TipoPagamentoEnum.MERCADO_PAGO);
    }

    public static Pagamento gerarPagamento() {
        return new Pagamento(new ObjectId(), 100.0, EstadoPagamentoEnum.PAGO, 213L, LocalDate.now(), LocalDate.now(), TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "123456");
    }
}
