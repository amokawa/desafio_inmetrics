package br.com.inmetrics.teste.api;

public class ApiRequestOptions {

    private String method;
    private String endpoint;
    private String payload;

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "ApiRequestOptions{" +
                "method='" + method + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
