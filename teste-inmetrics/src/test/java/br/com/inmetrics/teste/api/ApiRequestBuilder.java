package br.com.inmetrics.teste.api;

import br.com.inmetrics.teste.support.MyProperties;
import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiRequestBuilder {
    private final ApiRequestOptions apiRequestOptions;
    private final MyProperties myProperties = new MyProperties();

    public ApiRequestBuilder(ApiRequestOptions apiRequestOptions) {
        this.apiRequestOptions = apiRequestOptions;
    }

    public ApiRequest build() {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
        specBuilder.setBaseUri(myProperties.getProperty("api.base.url"));
        String method = apiRequestOptions.getMethod();
        if (method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put")) {
            specBuilder.setContentType(ContentType.JSON);
            specBuilder.setBody(apiRequestOptions.getPayload());
        }
        BasicAuthScheme auth = new BasicAuthScheme();
        auth.setUserName("inmetrics");
        auth.setPassword("automacao");
        specBuilder.setAuth(auth);
        RequestSpecification requestSpecification = specBuilder.build();
        RequestSpecification request = RestAssured.given().spec(requestSpecification);
        return new ApiRequestImpl(method, apiRequestOptions.getEndpoint(), request);
    }
}
