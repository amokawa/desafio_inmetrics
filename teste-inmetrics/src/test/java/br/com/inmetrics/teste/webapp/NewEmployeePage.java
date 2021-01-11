package br.com.inmetrics.teste.webapp;

import br.com.inmetrics.teste.support.MyProperties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewEmployeePage implements PageObject {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final MyProperties myProperties;
    private final By inputName = By.xpath("//input[@name='nome']");
    private final By inputPosition = By.xpath("//input[@name='cargo']");
    private final By inputDocument = By.xpath("//input[@name='cpf']");
    private final By inputSalary = By.xpath("//input[@name='salario']");
    private final By selectGender = By.xpath("//select[@name='sexo']");
    private final By inputEmployee = By.xpath("//input[@type='radio' and @id='clt']");
    private final By inputContractor = By.xpath("//input[@type='radio' and @id='pj']");
    private final By inputAdmission = By.xpath("//input[@name='admissao']");
    private final By inputSubmit = By.xpath("//input[@type='submit']");

    public NewEmployeePage(MyWebDriver myWebDriver) {
        driver = myWebDriver.getDriver();
        wait = myWebDriver.getWait();
        myProperties = new MyProperties();
    }

    @Override
    public void load() {
        driver.get(myProperties.getProperty("employees.new_employee.url"));
        wait.until(ExpectedConditions.presenceOfElementLocated(inputName));
    }

    @Override
    public boolean isLoaded() {
        return !driver.findElements(inputName).isEmpty();
    }

    public void fillName(String name) {
        WebElement element = driver.findElement(inputName);
        element.clear();
        element.sendKeys(name);
    }

    public void fillPosition(String position) {
        WebElement element = driver.findElement(inputPosition);
        element.clear();
        element.sendKeys(position);
    }

    public void fillDocument(String document) {
        WebElement element = driver.findElement(inputDocument);
        if (!element.getText().equals("")) {
            element.clear();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        element.sendKeys(document);
    }

    public void fillSalary(String salary) {
        WebElement element = driver.findElement(inputSalary);
        element.clear();
        element.sendKeys(salary);
    }

    public void selectGender(String gender) {
        Select select = new Select(driver.findElement(selectGender));
        select.selectByVisibleText(gender);
    }

    public void fillType(String type) {
        By by;
        switch (type.toLowerCase()) {
            case "clt":
                by = inputEmployee;
                break;
            case "pj":
                by = inputContractor;
                break;
            default:
                throw new RuntimeException("Unsupported employee type: " + type);
        }
        driver.findElement(by).click();
    }

    public void fillAdmission(String admissionDate) {
        WebElement element = driver.findElement(inputAdmission);
        element.clear();
        element.sendKeys(admissionDate);
    }

    public void submit() {
        driver.findElement(inputSubmit).click();
    }
}
