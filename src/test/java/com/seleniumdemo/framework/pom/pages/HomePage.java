package com.seleniumdemo.framework.pom.pages;

import com.seleniumdemo.framework.pom.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private final By storePageLink = By.xpath("//li[@id='menu-item-1227']/a[@class='menu-link' and text()='Store']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to store page")
    public StorePage navigateToStore(){
        wait.until(ExpectedConditions.elementToBeClickable(storePageLink)).click();
        return new StorePage(driver);
    }

    public HomePage loadWebPage(){
        loadWebPage("/");
        return this;
    }
}
