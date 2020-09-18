package pages;

import base.BasePage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.ITestResult;
import org.testng.annotations.*;
import screens.AmazonLoginPage;
import screens.AmazonProductsPage;
import util.TestUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import static util.TestUtil.*;

public class AmazonShopTest extends BaseTest {

    AmazonLoginPage loginPage;
    AmazonProductsPage productsPage;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass()  {


        log("Before class Amazonshop test................");
        String dataFileName ="data\\loginUsers.json";
        loginUsers= getInstance().getJSONData(dataFileName);
        loginPage =launchPage(AmazonLoginPage.class);
    }

    @BeforeMethod
    public void beforeMethod(Method method)
    {
        log("Before method  Amazonshop test................");
        closeApp();
        launchApp();
    }

//    @Test(priority = 1)
//    public void amazonTest() {
//          log("Before method  Amazonshop test................");
//           productsPage =loginPage.skipLogin();
//           loginUsers.getJSONObject("invalidUser").getString("userName");
//           productsPage.enterProductSearch("55 inch smart tv");
//    }



    @Test(priority = 1)
    public void amazonTest2() {
        loginPage.skipLogin();
        String userName =loginUsers.getJSONObject("validUserAndPassword").getString("userName");
        String password =loginUsers.getJSONObject("validUserAndPassword").getString("password");
        productsPage = loginPage.login(userName,password);
        productsPage.enterProductSearch("55 inch smart tv");
        //select product and add to cart
    }


    @AfterMethod
    public void afterMethod() {
        log("After method of amazonshoptest.............");;
    }
}

