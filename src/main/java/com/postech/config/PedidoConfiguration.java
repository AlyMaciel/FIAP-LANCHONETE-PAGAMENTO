package com.postech.config;

import com.postech.infra.client.PedidoClienteImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfiguration {

    @Value("${backend.pedido.url}")
    private String pedidoUrl;

    @Bean
    public PedidoClienteImpl pedidoClient() {
        return new PedidoClienteImpl(pedidoUrl);
    }
}
