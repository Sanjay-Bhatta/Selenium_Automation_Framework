package com.test;

import com.seleniumdemo.framework.pom.api.actions.CartAPI;
import com.seleniumdemo.framework.pom.api.actions.SignUpAPI;
import com.seleniumdemo.framework.pom.base.BaseTest;
import com.seleniumdemo.framework.pom.objects.Product;
import com.seleniumdemo.framework.pom.objects.User;
import com.seleniumdemo.framework.pom.pages.CheckOutPage;
import com.seleniumdemo.framework.pom.pages.LoginPage;
import com.seleniumdemo.framework.pom.utils.FakerUtils;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginDuringCheckoutTest extends BaseTest {
    @Test(description = "Test Login during Checkout")
    @Description("Test Login during Checkout")
    public void testLoginDuringCheckout() throws IOException {
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
        CheckOutPage checkOutPagePage = new CheckOutPage(getDriver()).loadCheckoutPage();
        injectCookiesToBrowser(cartAPI.getCookies());
        checkOutPagePage.loadCheckoutPage();
        checkOutPagePage.clickOnLoginLink().login(user);
        Assert.assertTrue(checkOutPagePage.getProductTextInOrderDetails().contains(product.getName()));


    }
}
