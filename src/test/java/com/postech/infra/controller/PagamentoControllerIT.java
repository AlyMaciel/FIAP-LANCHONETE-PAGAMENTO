package com.postech.infra.controller;

import com.postech.config.EmbeddedMongoConfig;
import com.postech.infra.dto.request.PagamentoRequestDTO;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import com.postech.utils.PagamentoHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
class PagamentoControllerIT {

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
    void devePermitirCriarPagamento()  {

        var pagamentoRequest = PagamentoHelper.gerarPagamentoRequest();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .when()
                .post("/lanchonete/v1/pagamentos")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveGerarExcecao_QuandoDerErroAoGerarPagamento()  {
        var pagamentoRequest = PagamentoHelper.gerarPagamentoRequest();
        pagamentoRequest.getPedidoDTO().setValorTotal(null);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .when()
                .post("/lanchonete/v1/pagamentos")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void devePermitirPegarEstadoPagamento()  {
        var pagamento = PagamentoHelper.gerarPagamentoEntidade();

        pagamentoRepository.save(pagamento);

        PagamentoRequestDTO pagamentoRequestDTO = PagamentoHelper.gerarPagamentoRequest();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequestDTO)
                .when()
                .get("/lanchonete/v1/pagamentos")
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    void deveGerarExcecao_QuandoNaoAcharPagamento_PorIdPedido()   {
        PagamentoRequestDTO pagamentoRequestDTO = PagamentoHelper.gerarPagamentoRequest();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequestDTO)
                .when()
                .get("/lanchonete/v1/pagamentos")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


}