package com.seleniumdemo.framework.pom.utils;

import com.seleniumdemo.framework.pom.constants.EnvType;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        String configFilePath = null;
        String env = System.getProperty("environment");
        if(StringUtils.isBlank(env)) {
            env = "PROD";
        }
        try {
            switch (EnvType.valueOf(env)){
                case PROD:
                    configFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "resources" + File.separator + "prod_config.properties";
                break;
                case STAGE:
                    configFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "resources" + File.separator + "staging_config.properties";
                    System.out.println("I am in stage");
                    break;
                case DEV:
                    configFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "resources" + File.separator + "dev_config.properties";
                    break;
                default:
                    throw new IllegalStateException("Invalid environment property file : " + env);
            }
            properties = PropertyUtils.propertyLoader(configFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load property file");
        }
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseURL(){
        String prop = properties.getProperty("BASE_URL");
        if(StringUtils.isBlank(prop)){
            throw new RuntimeException("Property 'BASE_URL' is not defined");
        }else {
            return prop;
        }
    }

    public String getUserName(){
        String prop = properties.getProperty("USERNAME");
        if(StringUtils.isBlank(prop)){
            throw new RuntimeException("Property 'USERNAME' is not defined");
        }else {
            return prop;
        }
    }

    public String getPassword(){
        String prop = properties.getProperty("PASSWORD");
        if(StringUtils.isBlank(prop)){
            throw new RuntimeException("Property 'PASSWORD' is not defined");
        }else {
            return prop;
        }
    }
}
