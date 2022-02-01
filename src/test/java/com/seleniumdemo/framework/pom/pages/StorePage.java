package com.seleniumdemo.framework.pom.pages;

import com.seleniumdemo.framework.pom.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StorePage extends BasePage {
    private String productId = null;
    private final By searchField = By.xpath("//input[@type='search' and @id='woocommerce-product-search-field-0']");
    private final By searchBtn = By.xpath("//button[@type='submit' and @value='Search']");
    private final By searchTitle = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By storeSummary = By.className("astra-shop-summary-wrap");
    private final By productTitle = By.className("woocommerce-loop-product__title");
    private final By addToCartBtn = By.className("add_to_cart_button");
    private String addSpecificItemToCartBtnXpath = "//a[@href='?add-to-cart=%s']";
    private final By viewCart = By.xpath("//a[@title='View cart']");

    public StorePage(WebDriver driver) {
        super(driver);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public StorePage loadStorePage(){
        loadWebPage("/store");
        return this;
    }

    @Step("Searching for product")
    public StorePage searchForProduct(String productName){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(searchField));
        element.clear();
        element.sendKeys(productName);
        return this;
    }
    @Step("Clicking on Search button")
    public StorePage clickOnSearchButton(){
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        return this;
    }
    @Step("Get Header Text")
    public String getSearchPageHeaderText(String expectedText){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(searchTitle, expectedText));
        return driver.findElement(searchTitle).getText();
    }
    @Step("Get Product Id")
    public StorePage getProductIdOfProduct(String productName){
        List<WebElement> getItems = driver.findElements(storeSummary);
        for(WebElement w : getItems){
            if(w.findElement(productTitle).getText().trim().equals(productName)){
                this.productId = w.findElement(addToCartBtn).getAttribute("data-product_id");
                break;
            }
        }
        return this;
    }
    private By getAddToCartBtm(String productName){
        return By.cssSelector("a[aria-label=\"Add “"+productName+"” to your cart\"]");
    }
    @Step("Click on add to cart button")
    public StorePage clickAddToCartButton(String productName){
        By addToCartBtn = getAddToCartBtm(productName);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        return this;
    }
    @Step("Add item to cart")
    public StorePage addSpecificItemToCart(String productId){
        wait.until(ExpectedConditions.elementToBeClickable
                        (By.xpath(String.format(addSpecificItemToCartBtnXpath, productId)))).click();
        return this;
    }
    @Step("Click on view cart")
    public CartPage clickOnViewCart(){
        wait.until(ExpectedConditions.elementToBeClickable(viewCart)).click();
        return new CartPage(driver);
    }
}
