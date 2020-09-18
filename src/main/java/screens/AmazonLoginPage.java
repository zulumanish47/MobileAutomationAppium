package screens;

import base.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonLoginPage extends BasePage {

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/skip_sign_in_button")
     private MobileElement skipSignIn;

    @AndroidFindBy(id ="gwm-SignIn-button")
    private MobileElement signIn;

    @AndroidFindBy(id = "ap_email_login")
    private MobileElement userNameLocator;

    @AndroidFindBy(id="continue")
    private MobileElement continueLocator;


    @AndroidFindBy(id ="ap_password")
    private MobileElement passwordLocator;

    @AndroidFindBy(id ="signInSubmit")
    private MobileElement submit;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='ap_email_login']/preceding-sibling::android.view.View[1]")
    private MobileElement countrySelector;

    @AndroidFindBy(xpath = "    //android.view.View[@resource-id='country_bottom_sheet_container_signin']/android.widget.ListView/android.view.View[contains(@text,'+ 61')]\n")
    private MobileElement countryChoiceLocator;

    public AmazonLoginPage(AppiumDriver driver)
    {
        super(driver);
        log("Launching Object of :"+AmazonLoginPage.class.getSimpleName());

    }

     public AmazonProductsPage skipLogin()
    {
        waitforVisibility(skipSignIn);
        click(skipSignIn);
        return new AmazonProductsPage(getDriver());
    }

    public AmazonProductsPage login(String userName,String password)
    {
        waitforVisibility(signIn);
        click(signIn);

      //  clickAt();
        click(userNameLocator);
        sendKeys(userNameLocator,userName);
        click(countrySelector);
        scrollToElement();
       // scroll(countryChoiceLocator);
        try{
            Thread.sleep(10000);
    }
        catch(Exception e)
        {

        }
        click(countryChoiceLocator);
        click(continueLocator);
        sendKeys(passwordLocator,password);
        click(submit);

        return launchPage(AmazonProductsPage.class);
    }


//

   //ap_email_login
//android.Widget.Edittext[@resource-id='ap_email_login']
    //hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/
    // android.widget.FrameLayout/android.widget.RelativeLayout/
    // android.webkit.WebView/android.webkit.WebView/android.view.View[4]/
    // android.view.View[1]/android.widget.ListView/android.view.View[11]

    //continue
    //signInSubmit
    //ap_password


}



