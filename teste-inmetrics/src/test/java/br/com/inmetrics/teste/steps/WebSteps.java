package br.com.inmetrics.teste.steps;

import br.com.inmetrics.teste.api.ApiRequestBuilder;
import br.com.inmetrics.teste.api.ApiRequestOptions;
import br.com.inmetrics.teste.support.MyStepsHelper;
import br.com.inmetrics.teste.webapp.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class WebSteps {

    private MyWebDriver myWebDriver;
    private PageObject page;

    @Before("@webapp")
    public void setUpWebTest() {
        myWebDriver = new MyWebDriver();
    }

    @After("@webapp")
    public void tearDownWebTest(Scenario scenario) {
        storeScreenshotEvidence(scenario);
        myWebDriver.getDriver().close();
        myWebDriver.getDriver().quit();
    }

    private void storeScreenshotEvidence(Scenario scenario) {
        try {
            File screenshot = ((TakesScreenshot) myWebDriver.getDriver()).getScreenshotAs(OutputType.FILE);
            String screenshotFileName = getStatus(scenario) + "_" + scenario.getName() + "_" + System.currentTimeMillis();
            String screenshotPath = getEvidenceFilename(getTargetDir(), screenshotFileName);
            Path target = new File(screenshotPath).toPath();
            Files.copy(screenshot.toPath(), target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getEvidenceFilename(File targetDir,
                                       String evidenceFilename) {
        String evidenceFilePath = targetDir.getPath() + "/screenshot.png";
        evidenceFilePath = evidenceFilePath.replace("/", File.separator);
        evidenceFilePath = evidenceFilePath.replace("screenshot", evidenceFilename);
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

    @Given("o usuário clica no link {string}")
    public void userClicksOnEmployeesPageLink(String link) {
        EmployeesPage employeesPage = new EmployeesPage(myWebDriver);
        myWebDriver.getWait().until(driver -> employeesPage.isLoaded());
        this.page = employeesPage.clickOn(link);
    }

    @Given("o usuário busca pelo funcionário chamado {string}")
    public void searchEmployee(String employeeName) {
        EmployeesPage employeesPage = new EmployeesPage(myWebDriver);
        this.page = employeesPage;
        myWebDriver.getWait().until(driver -> employeesPage.isLoaded());
        employeesPage.search(employeeName);
    }

    @Given("o usuário clica no ícone {string} do primeiro resultado listado")
    public void clickOnIconOfFirstEmployeeListed(String icon) {
        EmployeesPage page = (EmployeesPage) this.page;
        this.page = page.clickOn(icon);
    }

    @When("o formulário de cadastro de funcionário é enviado com os valores abaixo")
    public void userFillsNewEmployeeForm(List<Map<String, String>> entries) {
        NewEmployeePage newEmployeePage = (NewEmployeePage) this.page;
        for (Map<String, String> entry : entries) {
            newEmployeePage.fillName(MyStepsHelper.getUniqueUsername(entry.get("NAME")));
            newEmployeePage.fillPosition(entry.get("POSITION"));
            newEmployeePage.fillDocument(entry.get("DOCUMENT"));
            newEmployeePage.fillSalary(entry.get("SALARY"));
            newEmployeePage.selectGender(entry.get("GENDER"));
            newEmployeePage.fillType(entry.get("TYPE"));
            newEmployeePage.fillAdmission(entry.get("ADMISSION"));
            newEmployeePage.submit();
        }
    }

    @When("o formulário de cadastro é enviado com os seguintes dados")
    public void submitUserRegistrationForm(List<Map<String, String>> entries) {
        UserSignupPage signupPage = (UserSignupPage) App.getPage(AppPage.SIGNUP, myWebDriver);
        this.page = signupPage;
        signupPage.load();
        for (Map<String, String> entry : entries) {
            String username = MyStepsHelper.getUniqueUsername(entry.get("USER"));
            String password = entry.get("PASSWORD");
            String confirmPass = entry.get("CONFIRM_PASS");
            System.out.format("\nCriando usuário com os dados: %s, %s, %s\n", username, password, confirmPass);
            signupPage.fillUsername(username);
            signupPage.fillPassword(password);
            signupPage.fillConfirmPass(confirmPass);
            signupPage.submit();
        }
    }

    @When("o formulário de login é enviado com os valores abaixo")
    public void submitLoginForm(List<Map<String, String>> entries) {
        LoginPage loginPage = (LoginPage) App.getPage(AppPage.LOGIN, myWebDriver);
        this.page = loginPage;
        loginPage.load();
        for (Map<String, String> entry : entries) {
            String username = MyStepsHelper.getUniqueUsername(entry.get("USER"));
            String password = entry.get("PASSWORD");
            System.out.format("\nRealizando login com o usuário: %s, %s\n", username, password);
            loginPage.fillUser(username);
            loginPage.fillPassword(password);
            loginPage.submit();
        }
    }

    @Then("a página {string} é carregada")
    public void assertPageIsLoaded(String page) {
        page = page.replaceAll("\\s", "_");
        page = page.toUpperCase();
        String finalPage = page;
        Optional<AppPage> first = Arrays.stream(AppPage.values()).filter(appPage -> appPage.name().equals(finalPage)).findFirst();
        if (first.isPresent()) {
            this.page = App.getPage(first.get(), myWebDriver);
            Assert.assertTrue(this.page.isLoaded());
        } else {
            Assert.fail(String.format("The expected page '%s' was not loaded", finalPage));
        }
    }

    @Then("é verificado que o alerta de sucesso é exibido")
    public void assertSuccessAlertIsDisplayed() {
        EmployeesPage page = (EmployeesPage) this.page;
        Assert.assertTrue(page.isSuccessful());
    }
}
