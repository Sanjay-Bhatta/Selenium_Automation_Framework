package com.seleniumdemo.framework.pom.api.actions;

import com.seleniumdemo.framework.pom.objects.User;
import com.seleniumdemo.framework.pom.utils.ConfigLoader;
import com.seleniumdemo.framework.pom.utils.FakerUtils;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SignUpAPI {
    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    @Step("Fetch account details")
    private Response getAccount(){
        Cookies cookies = new Cookies();
        Response response = given()
                .baseUri(ConfigLoader.getInstance().getBaseURL())
                .cookies(cookies)
                .log()
                .all().
        when()
                .get("/account").
        then()
                .log()
                .all()
                .extract()
                .response();
        if(response.statusCode() != HttpStatus.SC_OK){
            throw new RuntimeException("Failed to fetch the account");
        }
        return response;
    }

    @Step("Register new user")
    public Response registerNewUser(User user){
        Cookies cookies = new Cookies();
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        Map<String, String> formParams = new HashMap<String, String>();
        formParams.put("username", user.getUsername());
        formParams.put("password", user.getPassword());
        formParams.put("email", user.getEmailAddress());
        formParams.put("woocommerce-register-nonce", fetchRegisterNonceValue());
        formParams.put("register", "Register");

        Response response = given()
                .baseUri(ConfigLoader.getInstance().getBaseURL())
                .headers(headers)
                .cookies(cookies)
                .formParams(formParams)
                .log()
                .all().
                when()
                .post("/account").
                then()
                .log()
                .all()
                .extract()
                .response();
        if(response.statusCode() != HttpStatus.SC_MOVED_TEMPORARILY){
            throw new RuntimeException("Failed to register new user");
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }

    @Step("Fetch register nonce value")
    private String fetchRegisterNonceValue(){
        Response response = getAccount();
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element element = document.selectFirst("#woocommerce-register-nonce");
        return element.attr("value");
    }
}
