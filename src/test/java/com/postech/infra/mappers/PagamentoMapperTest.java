package com.postech.infra.mappers;

import com.postech.domain.entities.Pagamento;
import com.postech.infra.dto.response.PagamentoResponseDTO;
import com.postech.infra.persistence.entities.PagamentoEntity;
import com.postech.utils.TesteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoMapperTest {


    private PagamentoMapper pagamentoMapper;

    @BeforeEach
    public void setUp() {
        pagamentoMapper = Mappers.getMapper(PagamentoMapper.class);
    }

    @Test
    void testParaEntidade() {
        Pagamento pagamento = TesteHelper.gerarPagamento();

        PagamentoEntity pagamentoEntity = pagamentoMapper.paraEntidade(pagamento);


        assertNotNull(pagamentoEntity);
    }

    @Test
    void testParaDominio() {
        PagamentoEntity pagamentoEntity = TesteHelper.gerarPagamentoEntidade();

        Pagamento pagamento = pagamentoMapper.paraDominio(pagamentoEntity);

        assertNotNull(pagamento);
    }

    @Test
    void testParaResponseDTO() {
        Pagamento pagamento = TesteHelper.gerarPagamento();

        PagamentoResponseDTO pagamentoResponseDTO = pagamentoMapper.paraResponseDTO(pagamento);

        assertNotNull(pagamentoResponseDTO);
    }

    @Test
    void testParaEntidadeLista() {
        Pagamento pagamento = TesteHelper.gerarPagamento();
        List<Pagamento> pagamentos = Collections.singletonList(pagamento);

        List<PagamentoEntity> pagamentoEntities = pagamentoMapper.paraEntidadeLista(pagamentos);

        assertNotNull(pagamentoEntities);
        assertEquals(1, pagamentoEntities.size());
        // add more assertions to verify the mapping
    }

    @Test
    void testParaDominioLista() {
        PagamentoEntity pagamentoEntity = TesteHelper.gerarPagamentoEntidade();
        List<PagamentoEntity> pagamentoEntities = Collections.singletonList(pagamentoEntity);

        List<Pagamento> pagamentos = pagamentoMapper.paraDominioLista(pagamentoEntities);

        assertNotNull(pagamentos);
        assertEquals(1, pagamentos.size());
    }

}