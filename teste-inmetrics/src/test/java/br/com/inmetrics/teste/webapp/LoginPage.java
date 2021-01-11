package br.com.inmetrics.teste.webapp;

import br.com.inmetrics.teste.support.MyProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage implements PageObject{
    private final MyProperties properties;
    private final WebDriverWait wait;
    private final WebDriver driver;
    private final By user = By.xpath("//input[@name='username']");
    private final By password = By.xpath("//input[@name='pass']");
    private final By submit = By.xpath("//button[@class='login100-form-btn']");

    public LoginPage(MyWebDriver myWebDriver) {
        driver = myWebDriver.getDriver();
        wait = myWebDriver.getWait();
        properties = new MyProperties();
    }

    @Override
    public void load() {
        driver.get(properties.getProperty("user.login.url"));
    }

    @Override
    public boolean isLoaded() {
        return driver.getCurrentUrl().equals(properties.getProperty("user.login.url"));
    }

    public void fillUser(String username) {
        driver.findElement(user).sendKeys(username);
    }

    public void fillPassword(String password) {
        driver.findElement(this.password).sendKeys(password);
    }

    public void submit() {
        driver.findElement(this.submit).click();
    }
}
