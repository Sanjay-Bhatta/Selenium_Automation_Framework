package com.test;

import com.seleniumdemo.framework.pom.base.BaseTest;
import com.seleniumdemo.framework.pom.pages.HomePage;
import com.seleniumdemo.framework.pom.pages.StorePage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {
    @Test(description = "Test Navigation to Store page from Home page via main menu")
    @Description("Test Navigation to Store page from Home page via main menu")
    public void testNavigateFromHomeToStoreUsingMainMenu(){
        StorePage storePage = new HomePage(getDriver())
                .loadWebPage()
                .navigateToStore();
        Assert.assertEquals("Store", storePage.getSearchPageHeaderText("Store"));
    }
}
