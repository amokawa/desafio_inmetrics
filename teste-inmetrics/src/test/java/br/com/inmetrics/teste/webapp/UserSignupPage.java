package br.com.inmetrics.teste.webapp;

import br.com.inmetrics.teste.support.MyProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserSignupPage implements PageObject {
    private final WebDriver driver;
    private final MyProperties properties;
    private final WebDriverWait wait;
    private final By user = By.xpath("//input[@name='username']");
    private final By password = By.xpath("//input[@name='pass']");
    private final By confirmPass = By.xpath("//input[@name='confirmpass']");
    private final By signup = By.xpath("//button[@class='login100-form-btn']");

    public UserSignupPage(MyWebDriver myWebDriver) {
        driver = myWebDriver.getDriver();
        wait = myWebDriver.getWait();
        properties = new MyProperties();
    }

    @Override
    public void load() {
        driver.get(properties.getProperty("user.signup.url"));
        wait.until(ExpectedConditions.presenceOfElementLocated(user));
    }

    @Override
    public boolean isLoaded() {
        return driver.getCurrentUrl().equals(properties.getProperty("user.signup.url"));
    }

    public void fillUsername(String user) {
        driver.findElement(this.user).sendKeys(user);
    }

    public void fillPassword(String password) {
        driver.findElement(this.password).sendKeys(password);
    }

    public void fillConfirmPass(String confirmPass) {
        driver.findElement(this.confirmPass).sendKeys(confirmPass);
    }

    public void submit() {
        driver.findElement(this.signup).click();
    }
}
