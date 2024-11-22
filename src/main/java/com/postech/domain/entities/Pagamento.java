package com.postech.domain.entities;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoMetodoPagamento;
import com.postech.domain.enums.TipoPagamentoEnum;
import com.postech.domain.exceptions.DominioException;
import com.postech.domain.utils.ValidacaoUtils;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Pagamento {

    private ObjectId id;

    private Double valor;

    private EstadoPagamentoEnum estadoPagamento;

    private Long idPedido;

    private LocalDate dataPagamento;

    private LocalDate dataCriacaoPagamento;

    private TipoPagamentoEnum tipoPagamento;

    private TipoMetodoPagamento metodoPagamento;

    private String qrCode;

    private String pagamentoId;


    public Pagamento(ObjectId id, Double valor, EstadoPagamentoEnum estadoPagamento, Long idPedido, LocalDate dataPagamento, LocalDate dataCriacaoPagamento, TipoMetodoPagamento metodoPagamento, String qrCode, TipoPagamentoEnum tipoPagamento, String pagamentoId) {
        this.id = id;
        this.valor = valor;
        this.estadoPagamento = estadoPagamento;
        this.idPedido = idPedido;
        this.dataPagamento = dataPagamento;
        this.dataCriacaoPagamento = dataCriacaoPagamento;
        this.metodoPagamento = metodoPagamento;
        this.qrCode = qrCode;
        this.tipoPagamento = tipoPagamento;
        this.pagamentoId = pagamentoId;
        validaEntidade();
    }

    public void validaEntidade() throws DominioException {
        ValidacaoUtils.validaArgumentoNaoNulo(getEstadoPagamento(), "O estado de pagamento não pode estar vazio!");
        ValidacaoUtils.validaArgumentoNaoNulo(getIdPedido(), "O pedido não pode estar vazio");
        ValidacaoUtils.validaArgumentoNaoNulo(getMetodoPagamento(), "É necessário que tenha um método de pagamento");
        ValidacaoUtils.validaArgumentoNaoNulo(getTipoPagamento(), "É necessário que tenha um tipo de pagamento definido");
        ValidacaoUtils.validaArgumentoNaoNulo(getDataCriacaoPagamento(), "É necessário que tenha uma data de criação de pagamento");
        ValidacaoUtils.validaValorPositivo(getValor(), "O valor do pagamento não pode ser negativo!");
        ValidacaoUtils.validaArgumentoNaoNulo(getValor(), "O valor do pagamento não pode ser nulo");
    }


    public String getPagamentoId() {
        return pagamentoId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public ObjectId getId() {
        return id;
    }

    public Double getValor() {
        return valor;
    }

    public EstadoPagamentoEnum getEstadoPagamento() {
        return estadoPagamento;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public LocalDate getDataCriacaoPagamento() {
        return dataCriacaoPagamento;
    }

    public TipoPagamentoEnum getTipoPagamento() {
        return tipoPagamento;
    }

    public TipoMetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setEstadoPagamento(EstadoPagamentoEnum estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }
}
