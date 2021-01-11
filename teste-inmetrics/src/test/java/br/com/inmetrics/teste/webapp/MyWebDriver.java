package br.com.inmetrics.teste.webapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyWebDriver {
    public static final int TIME_OUT_IN_SECONDS = 60;
    public static final int SLEEP_IN_MILLIS = 1000;
    private final WebDriverWait wait;
    private final WebDriver driver;

    public MyWebDriver() {
        this.driver = new ChromeDriver();
        this.wait = new WebDriverWait(driver, TIME_OUT_IN_SECONDS, SLEEP_IN_MILLIS);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}
