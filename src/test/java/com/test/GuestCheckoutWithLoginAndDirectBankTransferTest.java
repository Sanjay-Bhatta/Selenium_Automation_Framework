package com.test;

import com.seleniumdemo.framework.pom.api.actions.CartAPI;
import com.seleniumdemo.framework.pom.api.actions.SignUpAPI;
import com.seleniumdemo.framework.pom.base.BaseTest;
import com.seleniumdemo.framework.pom.objects.BillingAddress;
import com.seleniumdemo.framework.pom.objects.Product;
import com.seleniumdemo.framework.pom.objects.User;
import com.seleniumdemo.framework.pom.pages.CheckOutPage;
import com.seleniumdemo.framework.pom.utils.FakerUtils;
import com.seleniumdemo.framework.pom.utils.JacksonUtils;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GuestCheckoutWithLoginAndDirectBankTransferTest extends BaseTest {
    @Test(description = "Testing Guest Checkout With Login and Direct Bank Transfer")
    @Description("Testing Guest Checkout With Login and Direct Bank Transfer")
    public void testGuestCheckoutWithLoginAndDirectBankTransfer() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("BillingDetails.json", BillingAddress.class);
        String userName = FakerUtils.generateRandomUsername();
        User user = new User()
                .setUsername(userName)
                .setPassword(userName)
                .setEmailAddress(userName + "@gmail.com");
        SignUpAPI signUpAPI = new SignUpAPI();
        signUpAPI.registerNewUser(user);
        CartAPI cartAPI = new CartAPI();
        Product product = new Product(1215);
        cartAPI.addToCart(product.getId(), 1);
        CheckOutPage checkOutPage = new CheckOutPage(getDriver()).loadCheckoutPage();
        injectCookiesToBrowser(cartAPI.getCookies());
        checkOutPage.loadCheckoutPage();
        checkOutPage.clickOnLoginLink().login(user);
        Assert.assertTrue(checkOutPage.getProductTextInOrderDetails().contains(product.getName()));
        Assert.assertEquals(checkOutPage.getCheckoutPageHeader(), "Checkout");
        checkOutPage.enterBillingDetails(billingAddress).clickOnPlaceOrderBtn();
        Assert.assertEquals(checkOutPage.getOrderPlacedText(), "Thank you. Your order has been received.");
    }

}
