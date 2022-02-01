package com.test;

import com.seleniumdemo.framework.pom.base.BaseTest;
import com.seleniumdemo.framework.pom.objects.BillingAddress;
import com.seleniumdemo.framework.pom.objects.User;
import com.seleniumdemo.framework.pom.objects.Product;
import com.seleniumdemo.framework.pom.pages.*;
import com.seleniumdemo.framework.pom.utils.ConfigLoader;
import com.seleniumdemo.framework.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyVeryFirstTest extends BaseTest {
   // @Test
    public void testGuestCheckoutWithDirectBankTransfer() throws IOException {
        String searchFor = "Blue";
        Product product = new Product(1215);
        BillingAddress billingAddress = JacksonUtils.deserializeJson("BillingDetails.json", BillingAddress.class);
        StorePage storePage = new HomePage(getDriver())
                .loadWebPage()
                .navigateToStore()
                .searchForProduct(searchFor)
                .clickOnSearchButton();
        Assert.assertEquals(storePage.getSearchPageHeaderText("Search results: “"+searchFor+"”"), "Search results: “"+searchFor+"”");
        storePage.getProductIdOfProduct(product.getName())
                .addSpecificItemToCart(storePage.getProductId());
        
        CartPage cartPage = storePage.clickOnViewCart();
        Assert.assertEquals(cartPage.getProductText(), product.getName());
        CheckOutPage checkOutPage = cartPage.clickOnCheckoutButton();
        Assert.assertEquals(checkOutPage.getCheckoutPageHeader(), "Checkout");
        checkOutPage.enterBillingDetails(billingAddress).clickOnPlaceOrderBtn();
        
        Assert.assertEquals(checkOutPage.getOrderPlacedText(), "Thank you. Your order has been received.");
    }

  //  @Test
    public void testGuestLoginCheckoutWithDirectBankTransfer() throws IOException {
        String searchFor = "Blue";
        Product product = new Product(1215);
        BillingAddress billingAddress = JacksonUtils.deserializeJson("BillingDetails.json", BillingAddress.class);
        User user = new User(ConfigLoader.getInstance().getUserName(), ConfigLoader.getInstance().getPassword());
        StorePage storePage = new HomePage(getDriver())
                .loadWebPage()
                .navigateToStore()
                .searchForProduct(searchFor)
                .clickOnSearchButton();
        
        Assert.assertEquals(storePage.getSearchPageHeaderText("Search results: “"+searchFor+"”"), "Search results: “"+searchFor+"”");
        storePage.getProductIdOfProduct(product.getName())
                .addSpecificItemToCart(storePage.getProductId());
        
        CartPage cartPage = storePage.clickOnViewCart();
        Assert.assertEquals(cartPage.getProductText(), product.getName());
        CheckOutPage checkOutPage = cartPage.clickOnCheckoutButton();
        
        Assert.assertEquals(checkOutPage.getCheckoutPageHeader(), "Checkout");
        // Login
        LoginPage loginPage = checkOutPage.clickOnLoginLink();
        
        loginPage.login(user).clickOnLoginBtn();
        
        checkOutPage.enterBillingDetails(billingAddress).clickOnPlaceOrderBtn();
        
        Assert.assertEquals(checkOutPage.getOrderPlacedText(), "Thank you. Your order has been received.");
    }
}
