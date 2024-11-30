package com.postech.infra.mercadopago.config;

import com.mercadopago.MercadoPagoConfig;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MercadoPagoConfigurationTest {

    @Test
    void mercadoPagoConfigurationSetsAccessToken() {
        new MercadoPagoConfiguration();
        assertEquals("APP_USR-2734679388176382-071714-c55e98f1f9f0665bbd25ca3993c17293-483752154", MercadoPagoConfig.getAccessToken());
    }

    @Test
    void mercadoPagoConfigurationAccessTokenIsNotNull() {
        new MercadoPagoConfiguration();
        assertNotNull(MercadoPagoConfig.getAccessToken());
    }


}