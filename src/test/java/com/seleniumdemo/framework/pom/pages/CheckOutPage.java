package com.seleniumdemo.framework.pom.pages;


import com.seleniumdemo.framework.pom.base.BasePage;
import com.seleniumdemo.framework.pom.objects.BillingAddress;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckOutPage extends BasePage {
    public CheckOutPage(WebDriver driver) {
        super(driver);
    }
    private By checkoutPageHeader = By.className("has-text-align-center");
    private By firstName = By.id("billing_first_name");
    private By lastName = By.id("billing_last_name");
    private By billingAddress1 = By.id("billing_address_1");
    private By billingCity = By.id("billing_city");
    private By billingPostCode = By.id("billing_postcode");
    private By billingEmail = By.id("billing_email");
    private By overLay = By.cssSelector("div.blockUI.blockOverlay");
    private By placeOrderBtn = By.id("place_order");
    private By orderPlacedText = By.cssSelector("p.woocommerce-thankyou-order-received");
    private By loginLink = By.className("showlogin");
    private By productName = By.cssSelector("td.product-name");

    public CheckOutPage loadCheckoutPage(){
        loadWebPage("/checkout");
        return this;
    }

    @Step("Get Checkout Page Header")
    public String getCheckoutPageHeader(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutPageHeader));
        return element.getText();
    }

    @Step("Enter billing address")
    public CheckOutPage enterBillingDetails(BillingAddress billingAddress){
        enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .enterBillingAddress1(billingAddress.getAddressLine1())
                .enterBillingCity(billingAddress.getCity())
                .enterBillingPostCode(billingAddress.getPostcode())
                .enterBillingEmail(billingAddress.getEmail());
        return this;
    }
    @Step("Enter first name")
    public CheckOutPage enterFirstName(String fname){
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(fname);
        return this;
    }
    @Step("Enter last name")
    public CheckOutPage enterLastName(String lname){
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(lname);
        return this;
    }
    @Step("Enter billing address")
    public CheckOutPage enterBillingAddress1(String billingAdd1){
        driver.findElement(billingAddress1).clear();
        driver.findElement(billingAddress1).sendKeys(billingAdd1);
        return this;
    }
    @Step("Enter billing city")
    public CheckOutPage enterBillingCity(String billCity){
        driver.findElement(billingCity).clear();
        driver.findElement(billingCity).sendKeys(billCity);
        return this;
    }
    @Step("Enter billing postcode")
    public CheckOutPage enterBillingPostCode(String billingPostalCode){
        driver.findElement(billingPostCode).clear();
        driver.findElement(billingPostCode).sendKeys(billingPostalCode);
        return this;
    }
    @Step("Enter billing email")
    public CheckOutPage enterBillingEmail(String billEmail){
        driver.findElement(billingEmail).clear();
        driver.findElement(billingEmail).sendKeys(billEmail);
        return this;
    }

    @Step("Click on place order button")
    public void clickOnPlaceOrderBtn(){
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(overLay)));
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
    }

    @Step("Get order placed text")
    public String getOrderPlacedText(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacedText)).getText();
    }
    @Step("Click on Login link")
    public LoginPage clickOnLoginLink(){
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver) ;
    }
    @Step("Get product name in order details screen")
    public String getProductTextInOrderDetails(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        return element.getText();
    }
}
