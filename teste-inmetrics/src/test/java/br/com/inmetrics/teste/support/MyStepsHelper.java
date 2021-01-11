package br.com.inmetrics.teste.support;

public class MyStepsHelper {

    public static final long CURRENT_TIME_MILLIS = System.currentTimeMillis();

    public static String getUniqueUsername(String username) {
        return username.concat(String.valueOf(CURRENT_TIME_MILLIS));
    }
}
