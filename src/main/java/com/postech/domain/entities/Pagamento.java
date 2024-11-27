package com.postech.domain.entities;

import com.google.common.base.Objects;
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
        ValidacaoUtils.validaArgumentoNaoNulo(getValor(), "O valor do pagamento não pode ser nulo");
        ValidacaoUtils.validaValorPositivo(getValor(), "O valor do pagamento não pode ser negativo!");
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equal(id, pagamento.id) && Objects.equal(valor, pagamento.valor) && estadoPagamento == pagamento.estadoPagamento && Objects.equal(idPedido, pagamento.idPedido) && Objects.equal(dataPagamento, pagamento.dataPagamento) && Objects.equal(dataCriacaoPagamento, pagamento.dataCriacaoPagamento) && tipoPagamento == pagamento.tipoPagamento && metodoPagamento == pagamento.metodoPagamento && Objects.equal(qrCode, pagamento.qrCode) && Objects.equal(pagamentoId, pagamento.pagamentoId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, valor, estadoPagamento, idPedido, dataPagamento, dataCriacaoPagamento, tipoPagamento, metodoPagamento, qrCode, pagamentoId);
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "valor=" + valor +
                ", estadoPagamento=" + estadoPagamento +
                ", idPedido=" + idPedido +
                ", dataPagamento=" + dataPagamento +
                ", dataCriacaoPagamento=" + dataCriacaoPagamento +
                ", tipoPagamento=" + tipoPagamento +
                ", metodoPagamento=" + metodoPagamento +
                ", qrCode='" + qrCode + '\'' +
                ", pagamentoId='" + pagamentoId + '\'' +
                '}';
    }
}
