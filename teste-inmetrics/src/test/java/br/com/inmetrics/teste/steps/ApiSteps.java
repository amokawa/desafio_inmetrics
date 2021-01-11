package br.com.inmetrics.teste.steps;

import br.com.inmetrics.teste.api.ApiRequest;
import br.com.inmetrics.teste.api.ApiRequestBuilder;
import br.com.inmetrics.teste.api.ApiRequestOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ApiSteps {

    private String payload;
    private static ApiRequest apiRequest;
    private File apiRequestOptionsFile;

    @Before("@api")
    public void setUpApiTest(Scenario scenario) {
        File targetDir = getTargetDir();
        String pathname = targetDir.getPath() + "/ApiRequestOptions_" + scenario.getName() + "_"
                + System.currentTimeMillis() + ".txt";
        apiRequestOptionsFile = new File(pathname);
    }

    @After("@api")
    public void tearDownApiTest(Scenario scenario) {
        String responseFilename = getStatus(scenario) + "_" + scenario.getName() + "_" + System.currentTimeMillis();
        String responsePath = getEvidenceFilename(getTargetDir(), responseFilename, "/response.txt", "response");
        String responseBodyPath = getEvidenceFilename(getTargetDir(), responseFilename, "/response_body.json", "response_body");
        storeApiResponseEvidence(responsePath);
        storeApiResponseBodyEvidence(responseBodyPath);
    }

    private void storeApiResponseBodyEvidence(String responseBodyPath) {
        if (apiRequest.getResponse() == null)  return;
        File file = new File(responseBodyPath);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(apiRequest.getResponse().asByteArray());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeApiResponseEvidence(String responsePath) {
        File responseFile = new File(responsePath);
        try {
            OutputStream outputStream = new FileOutputStream(responseFile);
            outputStream.write(apiRequest.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getEvidenceFilename(File targetDir,
                                       String evidenceFilename,
                                       String defaultFilename,
                                       String replaceable) {
        String evidenceFilePath = targetDir.getPath() + defaultFilename;
        evidenceFilePath = evidenceFilePath.replace("/", File.separator);
        evidenceFilePath = evidenceFilePath.replace(replaceable, evidenceFilename);
        return evidenceFilePath;
    }

    private String getStatus(Scenario scenario) {
        return scenario.isFailed() ? "FAIL" : "PASS";
    }

    private File getTargetDir() {
        File targetDir = new File("target/test-evidences");
        if (!targetDir.exists()) targetDir.mkdirs();
        return targetDir;
    }

    @Given("um payload")
    public void setPayload(String payload) {
        LocalDate previousDay = LocalDate.now().minusDays(1);
        DateTimeFormatter dd_mm_yyyy = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        payload = payload.replace("$dd_mm_yyyy", previousDay.format(dd_mm_yyyy));
        this.payload = payload;
    }

    @When("é enviado uma requisição {string} para {string}")
    public void sendRequest(String method, String endpoint) {
        if (endpoint.contains("$empregadoId")) {
            String empregadoId = apiRequest.getResponse().jsonPath().getString("empregadoId");
            endpoint = endpoint.replace("$empregadoId", empregadoId);
        }
        ApiRequestOptions apiRequestOptions = new ApiRequestOptions();
        apiRequestOptions.setMethod(method);
        apiRequestOptions.setEndpoint(endpoint);
        apiRequestOptions.setPayload(payload);
        storeApiTestEvidence(apiRequestOptions);
        apiRequest = new ApiRequestBuilder(apiRequestOptions).build();
        apiRequest.sendRequest();
    }

    private void storeApiTestEvidence(ApiRequestOptions apiRequestOptions) {
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(apiRequestOptionsFile);
            outputStream.write(apiRequestOptions.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("a resposta tem status {int}")
    public void assertResponseStatus(int expectedStatusCode) {
        assertEquals(expectedStatusCode, apiRequest.getResponse().getStatusCode());
    }

    @And("a resposta segue o schema")
    public void assertResponseSchema(String expectedSchema) {
        assertThat(apiRequest.getResponse().getBody().asString(), JsonSchemaValidator.matchesJsonSchema(expectedSchema));
    }
}
