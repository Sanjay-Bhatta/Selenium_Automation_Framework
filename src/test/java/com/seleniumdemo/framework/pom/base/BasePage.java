package com.seleniumdemo.framework.pom.base;

import com.seleniumdemo.framework.pom.utils.ConfigLoader;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    @Step("Loading page {endpoint}")
    public void loadWebPage(String endpoint){
        driver.get(ConfigLoader.getInstance().getBaseURL() + endpoint);
    }

}
