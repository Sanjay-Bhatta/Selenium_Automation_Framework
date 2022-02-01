package com.seleniumdemo.framework.pom.api.actions;

import com.seleniumdemo.framework.pom.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CartAPI {
    private Cookies cookies;

    public CartAPI() {
    }

    public CartAPI(Cookies cookies) {
        this.cookies = cookies;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    @Step("Add item to cart")
    public Response addToCart(int productId, int quantity){
        Cookies cookies = new Cookies();
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("product_sku", "");
        formParams.put("product_id", productId);
        formParams.put("quantity", quantity);
        if(cookies == null){
            cookies = new Cookies();
        }
        Response response = given()
                .baseUri(ConfigLoader.getInstance().getBaseURL())
                .headers(headers)
                .cookies(cookies)
                .formParams(formParams)
                .log()
                .all().
                when()
                .post("/?wc-ajax=add_to_cart").
                then()
                .log()
                .all()
                .extract()
                .response();
        if(response.statusCode() != HttpStatus.SC_OK){
            throw new RuntimeException("Failed to add product with id " + productId + " to cart");
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }
}
