package com.postech.infra.persistence.entities;

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


}

