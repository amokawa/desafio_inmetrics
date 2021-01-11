package br.com.inmetrics.teste.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.net.URISyntaxException;

public class ApiRequestImpl implements ApiRequest {
    private final String method;
    private final String endpoint;
    private final RequestSpecification requestSpecification;
    private Response response;

    public ApiRequestImpl(String method, String endpoint, RequestSpecification requestSpecification) {
        this.method = method;
        this.endpoint = endpoint;
        this.requestSpecification = requestSpecification;
    }

    @Override
    public void sendRequest() {
        URI uri = null;
        try {
            uri = new URI(endpoint);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        switch (method.toLowerCase()) {
            case "get":
                this.response = requestSpecification.get(uri);
                break;
            case "post":
                this.response = requestSpecification.post(uri);
                break;
            case "put":
                this.response = requestSpecification.put(uri);
                break;
            case "delete":
                this.response = requestSpecification.delete(uri);
                break;
            default:
        }
    }

    @Override
    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "ApiRequestImpl{" +
                "method='" + method + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", requestSpecification=" + requestSpecification +
                ", response=" + response +
                '}';
    }
}
