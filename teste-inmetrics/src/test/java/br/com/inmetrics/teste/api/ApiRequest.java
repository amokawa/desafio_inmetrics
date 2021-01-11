package br.com.inmetrics.teste.api;

import io.restassured.response.Response;

public interface ApiRequest {
    void sendRequest();

    Response getResponse();
}
