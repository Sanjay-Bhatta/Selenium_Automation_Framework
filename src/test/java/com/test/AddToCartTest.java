package com.test;

import com.seleniumdemo.framework.pom.base.BaseTest;
import com.seleniumdemo.framework.pom.objects.Product;
import com.seleniumdemo.framework.pom.pages.CartPage;
import com.seleniumdemo.framework.pom.pages.StorePage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class AddToCartTest extends BaseTest {
    @Test(description = "Test adding items to cart from Store Page")
    @Description("Test adding items to cart from Store Page")
    public void testAddToCartFromStorePage() throws IOException {
        StorePage storePage = new StorePage(getDriver());
        Product product = new Product(1215);
        CartPage cartPage  = storePage.loadStorePage()
                .getProductIdOfProduct(product.getName())
                .addSpecificItemToCart(storePage.getProductId())
                .clickOnViewCart();
        Assert.assertEquals(cartPage.getProductText(), product.getName());
    }
}
