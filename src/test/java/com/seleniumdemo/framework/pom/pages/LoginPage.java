package com.seleniumdemo.framework.pom.pages;

import com.seleniumdemo.framework.pom.base.BasePage;
import com.seleniumdemo.framework.pom.objects.User;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    private By userNameTxtField = By.id("username");
    private By passwordTxtField = By.id("password");
    private By loginBtn = By.cssSelector("button[name='login']");

    @Step("Enter username")
    public LoginPage enterUserName(String userName){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(userNameTxtField));
        element.clear();
        element.sendKeys(userName);
        return this;
    }
    @Step("Enter password")
    public LoginPage enterPassword(String password){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordTxtField));
        element.clear();
        element.sendKeys(password);
        return this;
    }
    @Step("Entering username and password")
    public LoginPage login(User user){
        enterUserName(user.getUsername());
        enterPassword(user.getPassword());
        return this;
    }
    @Step("Clicking on login button")
    public void clickOnLoginBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }
}
