package com.test;

import com.seleniumdemo.framework.pom.base.BaseTest;
import com.seleniumdemo.framework.pom.pages.StorePage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    @Test(description = "Test user is able to search a product successfully")
    @Description("Test user is able to search a product successfully")
    public void testSearchWithPartialMatch(){
        String searchFor = "Blue";
        StorePage storePage = new StorePage(getDriver()).
                loadStorePage().
                searchForProduct(searchFor)
                .clickOnSearchButton();
        Assert.assertEquals(storePage.getSearchPageHeaderText("Search results: “"+searchFor+"”"),
                "Search results: “"+searchFor+"”");
    }
}
