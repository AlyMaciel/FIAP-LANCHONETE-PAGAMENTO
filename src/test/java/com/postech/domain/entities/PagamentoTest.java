package com.postech.domain.entities;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.domain.exceptions.DominioException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PagamentoTest {

    @Test
    void deveCriarEntidade_SemErros(){
        var pagamento = new Pagamento(new ObjectId(), 5.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 1L, LocalDate.now(), LocalDate.now(), TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1");
        Assertions.assertNotNull(pagamento);
    }

    @Test
    void deveCriarEntidade_ComErro_EstadoPagamentoNulo(){
        var objectId = new ObjectId();
        var data = LocalDate.now();

        assertThatThrownBy(() -> new Pagamento(objectId, 5.0, null, 1L, data, data, TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1"))
                .isInstanceOf(DominioException.class)
                .hasMessage("O estado de pagamento não pode estar vazio!");
    }

    @Test
    void deveCriarEntidade_ComErro_IdPedidoNulo(){
        var objectId = new ObjectId();
        var data = LocalDate.now();

        assertThatThrownBy(() -> new Pagamento(objectId, 5.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, null, data, data, TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1"))
                .isInstanceOf(DominioException.class)
                .hasMessage("O pedido não pode estar vazio");
     }

    @Test
    void deveCriarEntidade_ComErro_MetodoPagamentoNulo(){
        var objectId = new ObjectId();
        var data = LocalDate.now();

        assertThatThrownBy(() -> new Pagamento(objectId, 5.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 1L, data, data, null, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1"))
                .isInstanceOf(DominioException.class)
                .hasMessage("É necessário que tenha um método de pagamento");
    }

    @Test
    void deveCriarEntidade_ComErro_TipoPagamentoNulo(){
        var objectId = new ObjectId();
        var data = LocalDate.now();

        assertThatThrownBy(() -> new Pagamento(objectId, 5.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 1L, data, data, TipoMetodoPagamento.PIX, "123456", null, "1"))
                .isInstanceOf(DominioException.class)
                .hasMessage("É necessário que tenha um tipo de pagamento definido");
    }

    @Test
    void deveCriarEntidade_ComErro_DataCriacaoPagamentoNulo(){
        var objectId = new ObjectId();
        var data = LocalDate.now();

        assertThatThrownBy(() -> new Pagamento(objectId, 5.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 1L, data, null, TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1"))
                .isInstanceOf(DominioException.class)
                .hasMessage("É necessário que tenha uma data de criação de pagamento");
    }

    @Test
    void deveCriarEntidade_ComErro_ValorNulo(){
        var objectId = new ObjectId();
        var data = LocalDate.now();

        assertThatThrownBy(() -> new Pagamento(objectId, null, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 1L, data, data, TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1"))
                .isInstanceOf(DominioException.class)
                .hasMessage("O valor do pagamento não pode ser nulo");
    }

    @Test
    void deveCriarEntidade_ComErro_ValorNegativo(){
        var objectId = new ObjectId();
        var data = LocalDate.now();

        assertThatThrownBy(() -> new Pagamento(objectId, -1.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 1L, data, data, TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1"))
                .isInstanceOf(DominioException.class)
                .hasMessage("O valor do pagamento não pode ser negativo!");
    }



}