package com.test;

import com.seleniumdemo.framework.pom.api.actions.CartAPI;
import com.seleniumdemo.framework.pom.base.BaseTest;
import com.seleniumdemo.framework.pom.objects.BillingAddress;
import com.seleniumdemo.framework.pom.objects.Product;
import com.seleniumdemo.framework.pom.pages.CheckOutPage;
import com.seleniumdemo.framework.pom.utils.JacksonUtils;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GuestCheckoutWithDirectBankTransferTest extends BaseTest {
    @Test(description = "Testing Guest Checkout With Direct Bank Transfer")
    @Description("Testing Guest Checkout With Direct Bank Transfer")
    public void testGuestCheckoutWithDirectBankTransfer() throws IOException {
        CartAPI cartAPI = new CartAPI();
        Product product = new Product(1215);
        BillingAddress billingAddress = JacksonUtils.deserializeJson("BillingDetails.json", BillingAddress.class);
        cartAPI.addToCart(product.getId(), 1);
        CheckOutPage checkOutPage = new CheckOutPage(getDriver()).loadCheckoutPage();
        injectCookiesToBrowser(cartAPI.getCookies());
        checkOutPage.loadCheckoutPage();
        Assert.assertEquals(checkOutPage.getCheckoutPageHeader(), "Checkout");
        checkOutPage.enterBillingDetails(billingAddress).clickOnPlaceOrderBtn();
        Assert.assertEquals(checkOutPage.getOrderPlacedText(), "Thank you. Your order has been received.");
    }

}
