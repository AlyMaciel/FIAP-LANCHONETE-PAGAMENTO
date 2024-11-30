package com.postech.infra.controller;

import com.postech.application.client.PedidoClient;
import com.postech.config.EmbeddedMongoConfig;
import com.postech.infra.persistence.repositories.PagamentoRepository;
import com.postech.utils.TesteHelper;
import de.flapdoodle.embed.mongo.MongodExecutable;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
class PagamentoControllerIT {

    @LocalServerPort
    private int port;

    @MockBean
    private PedidoClient pedidoClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PagamentoRepository pagamentoRepository;

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
    void devePermitirCriarPagamento()  {

        var pagamentoRequest = TesteHelper.gerarCriarPagamentoRequestDTO();

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
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();
        criarPagamentoRequestDTO.setValorTotal(null);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(criarPagamentoRequestDTO)
                .when()
                .post("/lanchonete/v1/pagamentos")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void devePermitirPegarEstadoPagamento()  {
        var pagamento = TesteHelper.gerarPagamentoEntidade();

        pagamentoRepository.save(pagamento);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/lanchonete/v1/pagamentos/{idPedido}", pagamento.getIdPedido())
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    void deveGerarExcecao_QuandoNaoAcharPagamento_PorIdPedido()   {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/lanchonete/v1/pagamentos/{idPedido}", 1L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


}