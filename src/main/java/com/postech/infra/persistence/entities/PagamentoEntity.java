package com.postech.infra.persistence.entities;

import com.postech.domain.enums.EstadoPagamentoEnum;
import com.postech.domain.enums.TipoPagamentoEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "pagamento")
public class PagamentoEntity {

    @Id
    private ObjectId id;

    private Double valor;

    @Enumerated(EnumType.STRING)
    private EstadoPagamentoEnum estadoPagamento;

    private Long idPedido;

    private LocalDate dataPagamento;

    private LocalDate dataCriacaoPagamento;

    private String metodoPagamento;

    private String qrCode;

    private String pagamentoId;

    @Enumerated(EnumType.STRING)
    private TipoPagamentoEnum tipoPagamento;


}

