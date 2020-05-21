package screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class MobileAppTest {

 public static void main(String[] args) throws MalformedURLException {


     DesiredCapabilities dc = new DesiredCapabilities();

     dc.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
     dc.setCapability("avd","Pixel_XL_API_27");
     dc.setCapability("platformName","android");
     dc.setCapability("app","C:\\Users\\manish meera\\Documents\\workspace_sais\\Amazon_shopping.apk");
//     dc.setCapability("appPackage","com.android.calculator2");
//     dc.setCapability("appActivity",".Calculator");
     dc.setCapability("appPackage", "com.amazon.mShop.android.shopping");
     dc.setCapability("appActivity", "com.amazon.mShop.splashscreen.StartupActivity");
     dc.setCapability("androidInstallTimeout",50000);
     dc.setCapability("automationName","UiAutomator1");


     AndroidDriver<AndroidElement> ad = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),dc);
    try{
        Thread.sleep(10000);
    }
    catch(Exception e)
     {


     }
     waitForPresence(ad,10000,"com.amazon.mShop.android.shopping:id/skip_sign_in_button");
     MobileElement el = ad.findElementById("com.amazon.mShop.android.shopping:id/skip_sign_in_button");
     el.click();


     MobileElement el1 = ad.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text");
     el1.click();
     el1.sendKeys("55 inch smart Tv");
      ad.pressKey(new KeyEvent(AndroidKey.ENTER));
    // MobileElement el1 = (MobileElement) ad.findElementById("com.android.calculator2:id/digit_9");
//     el1.click();
//     MobileElement el2 = (MobileElement) ad.findElementByAccessibilityId("plus");
//     el2.click();
//     MobileElement el3 = (MobileElement) ad.findElementById("com.android.calculator2:id/digit_1");
//     el3.click();
//     MobileElement el4 = (MobileElement) ad.findElementById("com.android.calculator2:id/digit_0");
//     el4.click();
//     MobileElement el5 = (MobileElement) ad.findElementByAccessibilityId("equals");
//     el5.click();
//     Assert.assertEquals(ad.findElementById("com.android.calculator2:id/result").getText(),"19");

 }

    public static MobileElement mobileElement;

    public static boolean waitForPresence(AndroidDriver driver, int timeLimitInSeconds, String targetResourceId){
        Boolean isElementPresent;
        try{
            mobileElement =  (MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+targetResourceId+"\")");
            WebDriverWait wait = new WebDriverWait(driver, timeLimitInSeconds);
            wait.until(ExpectedConditions.visibilityOf(mobileElement));
            isElementPresent = mobileElement.isDisplayed();
            return isElementPresent;
        }catch(Exception e){
            isElementPresent = false;
            System.out.println(e.getMessage());
            return isElementPresent;
        } }

}