package com.postech.utils;

import com.postech.application.utils.NotificacaoPagamento;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.infra.dto.request.CriarPagamentoRequestDTO;
import com.postech.infra.dto.response.PagamentoResponseDTO;
import com.postech.infra.persistence.entities.PagamentoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public abstract class TesteHelper {

    public static PagamentoEntity gerarPagamentoEntidade() {
        return new PagamentoEntity(new ObjectId(), 100.0, EstadoPagamentoEnum.PAGO, 1L, LocalDate.now(), LocalDate.now(), TipoMetodoPagamento.PIX, "123456", "1", TipoPagamentoEnum.MERCADO_PAGO);
    }

    public static Pagamento gerarPagamento() {
        return new Pagamento(new ObjectId(), 5.0, EstadoPagamentoEnum.PENDENTE_PAGAMENTO, 1L, LocalDate.now(), LocalDate.now(), TipoMetodoPagamento.PIX, "123456", TipoPagamentoEnum.MERCADO_PAGO, "1");
    }

    public static NotificacaoPagamento gerarNotificacaoPagamentoPago() {
        return new NotificacaoPagamento(EstadoPagamentoEnum.PAGO, LocalDate.now(), "1");
    }

    public static NotificacaoPagamento gerarNotificacaoPagamentoPendentePagamento() {
        return new NotificacaoPagamento(EstadoPagamentoEnum.PENDENTE_PAGAMENTO, LocalDate.now(), "1");
    }



    public static CriarPagamentoRequestDTO gerarCriarPagamentoRequestDTO(){
        return new CriarPagamentoRequestDTO(1L, 5.0, 1L);
    }


    public static PagamentoResponseDTO gerarPagamentoResponse(){
        return new PagamentoResponseDTO(100.0, EstadoPagamentoEnum.PAGO, LocalDate.now(), LocalDate.now(), TipoPagamentoEnum.MERCADO_PAGO, TipoMetodoPagamento.PIX, "qrCode", "123456", 1L);
    }



}
