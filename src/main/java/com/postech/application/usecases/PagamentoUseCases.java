package com.postech.application.usecases;

import com.postech.application.client.PedidoClient;
import com.postech.application.gateways.RepositorioDePagamentoGateway;
import com.postech.domain.entities.Pagamento;
import com.postech.domain.enums.ErroPagamentoEnum;
import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.exceptions.PagamentoException;
import com.postech.domain.interfaces.PagamentoInterface;
import com.postech.application.utils.NotificacaoPagamento;
import com.postech.infra.dto.request.CriarPagamentoRequestDTO;


public class PagamentoUseCases {

    private final RepositorioDePagamentoGateway repositorio;

    private final PagamentoInterface pagamentoExternoUseCase;

    private final PedidoClient pedidoCliente;

    public PagamentoUseCases(RepositorioDePagamentoGateway repositorio, PagamentoInterface pagamentoExternoUseCase, PedidoClient pedidoCliente) {
        this.repositorio = repositorio;
        this.pagamentoExternoUseCase = pagamentoExternoUseCase;
        this.pedidoCliente = pedidoCliente;
    }

    public Pagamento criarPagamentoPix(CriarPagamentoRequestDTO criarPagamentoRequestDTO) {
        try{
            Pagamento pagamento = pagamentoExternoUseCase.criarPagamento(criarPagamentoRequestDTO);
            return repositorio.salvaPagamento(pagamento);
        }catch (Exception e){
            throw new PagamentoException(ErroPagamentoEnum.ERRO_CRIAR_PAGAMENTO);
        }
    }


    public EstadoPagamentoEnum getStatusPagamento(Long idPedido){
        Pagamento pagamento = repositorio.consultaPagamentoPorIdPedido(idPedido);

        if(pagamento == null){
            throw new PagamentoException(ErroPagamentoEnum.PAGAMENTO_NAO_ENCONTRADO_POR_ID_PEDIDO);
        }

        return pagamento.getEstadoPagamento();
    }

    public Pagamento getPagamentoPorIdPagamento(String id){
        Pagamento pagamento = repositorio.consultaPagamentoPorIdPagamento(id);

        if(pagamento == null){
            throw new PagamentoException(ErroPagamentoEnum.PAGAMENTO_NAO_ENCONTRADO_POR_ID_PAGAMENTO);
        }

        return pagamento;
    }

    public void atualizaEstadoPagamento(NotificacaoPagamento notificacaoPagamento) {
        Pagamento pagamento = this.getPagamentoPorIdPagamento(notificacaoPagamento.getPagamentoId());

        pagamento.setEstadoPagamento(notificacaoPagamento.getEstadoPagamento());
        pagamento.setDataPagamento(notificacaoPagamento.getDataAttPagamento());

        repositorio.salvaPagamento(pagamento);

        if(pagamento.getEstadoPagamento().equals(EstadoPagamentoEnum.PAGO)) {
            pedidoCliente.enviaEstadoPagamento(pagamento.getIdPedido(), EstadoPagamentoEnum.PAGO, pagamento.getDataPagamento());
        }
    }
}
