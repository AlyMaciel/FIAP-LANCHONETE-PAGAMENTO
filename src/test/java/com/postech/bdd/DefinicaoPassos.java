package com.postech.bdd;

import com.postech.infra.dto.response.PagamentoResponseDTO;
import com.postech.utils.TesteHelper;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class DefinicaoPassos {

    private Response response;

    private final String BASE_URL = System.getenv("BASE_URL") != null
            ? System.getenv("BASE_URL")
            : "http://localhost:8080/lanchonete/v1";

    @Quando("submeter um novo pagamento")
    public PagamentoResponseDTO submeterNovoPagamento() {
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();
        response = given()
                .baseUri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(criarPagamentoRequestDTO)
                .when().post("/pagamentos");

        response.then().log().all();

        return response.then().extract().as(PagamentoResponseDTO.class);
    }

    @Então("o pagamento é registrado com sucesso")
    public void pagamentoRegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }


}
