package com.postech.infra.controller;

import com.postech.application.client.PedidoClient;
import com.postech.config.EmbeddedMongoConfig;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import com.postech.utils.TesteHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;


import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
class NotificacaoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @MockBean
    private PedidoClient pedidoClient;

    @Autowired
    private MongoTemplate mongoTemplate;


    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeAll
    static void startMongo(@Autowired MongodExecutable mongodExecutable) throws Exception {
        if (mongodExecutable != null) {
            mongodExecutable.start();
        }
    }

    @AfterAll
    static void stopMongo(@Autowired MongodExecutable mongodExecutable) {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();  // Limpa o banco de dados
    }

    @Test
    void devePermitirAtualizarPagamento() {
        var notificacaoRequest = "{\"action\":\"payment.updated\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{\"id\":\"1\"}}";

        var pagamento = TesteHelper.gerarPagamentoEntidade();

        pagamentoRepository.save(pagamento);

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(notificacaoRequest)
                .when()
                .post("/lanchonete/v1/notificacoes/mercadopago")
                .then()
                .log().all() // Loga toda a resposta
                .statusCode(HttpStatus.OK.value());

    }

}
