package br.com.inmetrics.teste.webapp;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.Map;

public class EmployeesPageLinks {
    private final MyWebDriver myWebDriver;
    private Map<String, By> icons;

    public EmployeesPageLinks(MyWebDriver myWebDriver) {
        this.myWebDriver = myWebDriver;
        initializeIcons();
    }

    private void initializeIcons() {
        icons = new HashMap<>();
        icons.put("editar", By.xpath("//tbody/tr[1]//span[@class='fa fa-pencil']/.."));
        icons.put("remover", By.xpath("//tbody/tr[1]//span[@class='fa fa-trash']/.."));
    }

    public PageObject clickOn(String text) {
        By xpath = icons.get(text.toLowerCase());
        if (xpath == null) {
            xpath = By.xpath(String.format("//a[contains(text(),'%s')]/..", text));
            myWebDriver.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath));
        }
        myWebDriver.getDriver().findElement(xpath).click();

        switch (text.toLowerCase()) {
            case "remover":
            case "funcionários":
                return new EmployeesPage(myWebDriver);
            case "editar":
            case "novo funcionário":
                return new NewEmployeePage(myWebDriver);
            case "sair":
                return new LoginPage(myWebDriver);
            default:
                throw new RuntimeException(String.format("\nLink was not found using %s\n", text));
        }
    }
}
