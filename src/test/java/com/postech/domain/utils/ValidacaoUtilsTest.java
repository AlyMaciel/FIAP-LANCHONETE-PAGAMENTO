package com.postech.domain.utils;

import com.postech.domain.exceptions.DominioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoUtilsTest {

    @Test
    void validaValorPositivo() {
        assertThrows(DominioException.class, () -> ValidacaoUtils.validaValorPositivo(-1.0, "Erro valor negativo"));
    }

    @Test
    void validaArgumentoNaoVazio_Vazio() {
        assertThrows(DominioException.class, () -> ValidacaoUtils.validaArgumentoNaoVazio("", "Erro string vazia"));
    }

    @Test
    void validaArgumentoNaoVazio_Nulo() {
        assertThrows(DominioException.class, () -> ValidacaoUtils.validaArgumentoNaoVazio(null, "Erro string nula"));
    }

    @Test
    void validaArgumentoNaoNulo() {
        assertThrows(DominioException.class, () -> ValidacaoUtils.validaArgumentoNaoNulo(null, "Erro objeto nulo"));
    }

}