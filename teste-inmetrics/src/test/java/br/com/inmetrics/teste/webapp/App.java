package br.com.inmetrics.teste.webapp;

import br.com.inmetrics.teste.steps.AppPage;

public class App {

    public static PageObject getPage(AppPage appPage, MyWebDriver myWebDriver) {
        switch (appPage) {
            case SIGNUP:
                return new UserSignupPage(myWebDriver);
            case LOGIN:
                return new LoginPage(myWebDriver);
            case EMPREGADOS:
                return new EmployeesPage(myWebDriver);
            default:
                throw new RuntimeException("Unexpected page: " + appPage.name());
        }
    }

}
