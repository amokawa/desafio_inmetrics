package br.com.inmetrics.teste.support;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    private static Properties properties;

    public MyProperties() {
        properties = new Properties();
        initializeProperties();
    }

    public String getProperty(String s) {
        return properties.getProperty(s);
    }

    private void initializeProperties() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configPath = rootPath + "config/config.properties";
        try {
            properties.load(new FileInputStream(configPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
