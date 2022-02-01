package com.seleniumdemo.framework.pom.pages;

import com.seleniumdemo.framework.pom.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {
    private By productName = By.cssSelector("td.product-name a");
    private By checkoutBtn = By.cssSelector(".checkout-button");
    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Product text")
    public String getProductText(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }
    @Step("Clicking on checkout button")
    public CheckOutPage clickOnCheckoutButton(){
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        return new CheckOutPage(driver);
    }
}
