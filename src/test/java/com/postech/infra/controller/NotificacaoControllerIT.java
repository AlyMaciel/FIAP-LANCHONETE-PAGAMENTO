package com.postech.infra.controller;

import com.postech.config.EmbeddedMongoConfig;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import com.postech.utils.PagamentoHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
public class NotificacaoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private MongodExecutable mongodExecutable;

    @BeforeEach
    public void setup() throws IOException {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        mongodExecutable.start();
    }

    @AfterEach
    void tearDown() {
        pagamentoRepository.deleteAll();
        mongodExecutable.stop();

    }

    @Test
    void devePermitirAtualizarPagamento() {
        var notificacaoRequest = "{\"action\":\"payment.updated\",\"date_created\":\"2024-07-29T22:00:00\",\"data\":{\"id\":\"123456\"}}";

        var pagamento = PagamentoHelper.gerarPagamentoEntidade();

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
