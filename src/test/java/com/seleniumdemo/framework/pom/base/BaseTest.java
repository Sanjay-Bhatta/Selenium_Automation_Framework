package com.seleniumdemo.framework.pom.base;

import com.seleniumdemo.framework.pom.constants.DriverType;
import com.seleniumdemo.framework.pom.factory.DriverManagerFactory;
import com.seleniumdemo.framework.pom.utils.CookieUtils;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public void setDriver(WebDriver driver){
        this.driver.set(driver);
    }

    protected WebDriver getDriver(){
        return this.driver.get();
    }
    @BeforeMethod(description = "Initializing web driver")
    public synchronized void startDriver(){
        String browserName = System.getProperty("browserName");
        if(StringUtils.isBlank(browserName)){
            browserName = "CHROME";
            System.setProperty("browserName", "CHROME");
        }
        setDriver(DriverManagerFactory.getDriverManager(DriverType.valueOf(browserName)).createDriver());
    }

    @AfterMethod(description = "Quitting web driver")
    public synchronized void quitDriver(ITestResult result) throws InterruptedException, IOException {
        Thread.sleep(100);
       if(result.getStatus() == ITestResult.SUCCESS || result.getStatus() == ITestResult.FAILURE){
            String screenShotName = result.getTestClass().getRealClass().getSimpleName().toUpperCase() + "_"
                    + result.getMethod().getMethodName().toUpperCase() + ".png";
            takeScreenShot(screenShotName);
       }
        getDriver().quit();
    }
    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookieList = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie : seleniumCookieList){
            getDriver().manage().addCookie(cookie);
        }
    }
    @Attachment(value = "{0}", type = "image/png")
    private byte[] takeScreenShot(String screenshotName) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        return takesScreenshot.getScreenshotAs(OutputType.BYTES);
    }

    private void takeScreenShotUsingAshot(File destinationFile){
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());
        try{
            ImageIO.write(screenshot.getImage(), "PNG", destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
