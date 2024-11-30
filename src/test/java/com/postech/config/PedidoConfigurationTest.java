package com.postech.config;


import com.postech.infra.client.PedidoClienteImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PedidoConfigurationTest {


    @InjectMocks
    private PedidoConfiguration pedidoConfiguration;


    AutoCloseable openMocks;


    @BeforeEach
    void setUp() {

        openMocks = MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void validPedidoUrl() {
        PedidoClienteImpl pedidoClient = pedidoConfiguration.pedidoClient();
        assertNotNull(pedidoClient);
    }

}

