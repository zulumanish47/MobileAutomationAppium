package screens;

import base.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonLoginPage extends BasePage {

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/skip_sign_in_button")
     private MobileElement skipSignIn;

     public AmazonProductsPage skipLogin()
    {
        waitforVisibility(skipSignIn);
        click(skipSignIn);
        return new AmazonProductsPage();
    }






}



