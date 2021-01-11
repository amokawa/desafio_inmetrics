package br.com.inmetrics.teste.webapp;

import br.com.inmetrics.teste.support.MyProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeesPage implements EmployeesPageObject {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By employeeListRows = By.xpath("//table/tbody/tr");
    private final By searchBox = By.xpath("//input[@type='search']");
    private final By successAlert = By.xpath("//div[contains(@class,'alert alert-success')]");
    private final MyProperties myProperties;
    private final EmployeesPageLinks employeesPageLinks;

    public EmployeesPage(MyWebDriver myWebDriver) {
        driver = myWebDriver.getDriver();
        wait = myWebDriver.getWait();
        myProperties = new MyProperties();
        employeesPageLinks = new EmployeesPageLinks(myWebDriver);
    }

    @Override
    public void load() {
        driver.get(myProperties.getProperty("user.employees.url"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(employeeListRows));
    }

    @Override
    public boolean isLoaded() {
        return !driver.findElements(employeeListRows).isEmpty();
    }

    public PageObject clickOn(String text) {
        return employeesPageLinks.clickOn(text);
    }

    public void search(String employeeName) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchBox));
        driver.findElement(searchBox).sendKeys(employeeName);
    }

    public boolean isSuccessful() {
        return driver.findElement(successAlert).isDisplayed();
    }
}
