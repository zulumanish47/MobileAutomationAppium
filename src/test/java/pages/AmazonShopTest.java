package pages;

import base.BasePage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import screens.AmazonLoginPage;
import screens.AmazonProductsPage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class AmazonShopTest extends BasePage {

    AmazonLoginPage loginPage;
    AmazonProductsPage productsPage;
    InputStream dataIS;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws IOException { try{
        String dataFileName ="data/loginUsers.json";
        dataIS=getClass().getClassLoader().getResourceAsStream(dataFileName);
        JSONTokener jsonTokener = new JSONTokener(dataIS);
        loginUsers = new JSONObject(jsonTokener);
    } catch (Exception e)
    {
       e.printStackTrace();
    }finally{
        if(dataIS!=null)
        {
            dataIS.close();
        }

    }
    }


    @BeforeMethod
    public void beforeMethod(Method method)
    {
        loginPage = new AmazonLoginPage();
    }



    @Test(priority = 1)
    public void amazonTest() {
           productsPage =loginPage.skipLogin();

          // loginUsers.getJSONObject("invalidUser").getString("userName");
           productsPage.enterProductSearch("55 inch smart tv");
    }

}

