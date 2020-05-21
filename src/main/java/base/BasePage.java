package base;

import driverfactory.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import util.TestUtil;
import util.WebEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class BasePage {

    public static AppiumDriver<?> ad;
    protected Properties props;
    //use input stream as file is in withing package level
    protected InputStream inputStream;
    protected InputStream stringsIS;
    TestUtil testUtils;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;
    public static DSLNew _dsl;
    private HashMap<String, String> Strings;

    public BasePage()
    {
        PageFactory.initElements(new AppiumFieldDecorator(ad),this);
    }

    @Parameters({"platformName","platformVersion","deviceName"})
    @BeforeTest
    public synchronized void setup(String platformName,String platformVersion ,String deviceName) throws IOException {
        try {
//            props = new Properties();
//            String propFileName= "config.properties";
//            inputStream =getClass().getClassLoader().getResourceAsStream(propFileName);
            String xmlFileName="strings/String.xml";
            stringsIS=getClass().getClassLoader().getResourceAsStream(xmlFileName);
    //        props.load(inputStream);
            testUtils =new TestUtil();
           Strings= testUtils.parseStringXML(stringsIS);
           ad= MobileDriverFactory.getMobileDriver(platformName);


//            DesiredCapabilities dc = new DesiredCapabilities();
//            dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
//            dc.setCapability("avd", "Pixel_XL_API_27");
//            dc.setCapability("platformName", platformName);
//            dc.setCapability("platformVersion",platformVersion);

//            URL appURL =getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
//            dc.setCapability("app", appURL);
//            dc.setCapability("appPackage", props.getProperty("androidAppPackage"));
//            dc.setCapability("appActivity", props.getProperty("androidAppActivity"));
//            dc.setCapability("androidInstallTimeout", 50000);
//            dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
//            dc.setCapability("automationName", props.getProperty("androidAutomationName"));


   //         URL url = new URL(props.getProperty("appiumURL"));

            // dc.setCapability(CapabilityType.PLATFORM_NAME,"android");
          //parent class of android and ios driver
          // ad = new AppiumDriver<MobileElement>(url,dc);

           //specific driver of android
   //        ad = new AndroidDriver<AndroidElement>(url, dc);
                     // ad = new IOSDriver<MobileElement>(url,dc);
        }
        catch(Exception ex)
        {
            System.out.println("Cause is :" +ex.getCause());
            System.out.println("Message is :" +ex.getMessage());
            ex.printStackTrace();

        }
        finally {
            if(inputStream!=null)
            {
                inputStream.close();
            }
            if(stringsIS!=null)
            {
                stringsIS.close();
            }
        }
    }





    @AfterTest
    public void teardown() {
        ad.quit();
    }

    public void waitforVisibility(MobileElement e)
    {
        WebDriverWait wait = new WebDriverWait(ad , TestUtil.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(MobileElement e)
    {
        waitforVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e , String txt)
    {
        waitforVisibility(e);
        e.sendKeys(txt);
    }

    public void getAttribute(MobileElement e , String attribute)
    {
        waitforVisibility(e);
        e.getAttribute(attribute);
    }

    public void pressEnter()
    {
        ((AndroidDriver<MobileElement>)ad).pressKey(new KeyEvent(AndroidKey.ENTER));
    }



//    public static boolean waitForPresence(AndroidDriver driver, int timeLimitInSeconds, String targetResourceId){
//        Boolean isElementPresent;
//        try{
//            mobileElement =  (MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+targetResourceId+"\")");
//            WebDriverWait wait = new WebDriverWait(driver, timeLimitInSeconds);
//            wait.until(ExpectedConditions.visibilityOf(mobileElement));
//            isElementPresent = mobileElement.isDisplayed();
//            return isElementPresent;
//        }catch(Exception e){
//            isElementPresent = false;
//            System.out.println(e.getMessage());
//            return isElementPresent;
//        } }
}
