package com.postech.bdd;

import com.postech.infra.dto.response.PagamentoResponseDTO;
import com.postech.utils.TesteHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class DefinicaoPassos {

    private Response response;

    private PagamentoResponseDTO pagamentoResponse;

    @Quando("submeter um novo pagamento")
    public PagamentoResponseDTO submeterNovoPagamento() {
        var criarPagamentoRequestDTO = TesteHelper.gerarCriarPagamentoRequestDTO();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(criarPagamentoRequestDTO)
                .when().post("/pagamentos");

        return response.then().extract().as(PagamentoResponseDTO.class);
    }

    @Então("o pagamento é registrado com sucesso")
    public void pagamentoRegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Dado("que um pagamento já foi realizado")
    public void pagamentoJaCriado() {
        pagamentoResponse = submeterNovoPagamento();
    }

    @Quando("requisitar a busca do estado do pagamento")
    public void requisitarBuscarEstadoPagamento() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/pagamentos/{idPedido}", pagamentoResponse.getIdPedido());
    }

    @Então("o estado do pagamento é exibido com sucesso")
    public void estadoPagamentoExibidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
}
