package base;

import ExtentReportListener.ExtentReport;
import com.aventstack.extentreports.Status;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import screens.screensInterface;
import util.GlobalParams;
import util.TestUtil;
import util.WebEventListener;

import java.time.Duration;
import java.util.HashMap;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public abstract class BasePage implements screensInterface {

    private AppiumDriver<?> ad;
    private static ThreadLocal<WebDriverWait> WAIT = new ThreadLocal<>();

   //  public static EventFiringWebDriver e_driver;
     public static WebEventListener eventListener;
   //  public static DSLNew _dsl;

    public BasePage()
    {
        log("Launching constructor of basepage");
    }

    public BasePage(AppiumDriver driver)
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
        this.ad =driver;
        setWAIT(140);
        log("Launching parametrised constructor of basepage");
    }

    private void setWAIT(int TimeinSeconds)
    {
        WAIT.set(new WebDriverWait(ad,TimeinSeconds));
    }


    public void waitforVisibility(MobileElement e)
    {
        log("Waiting for visiblity of element "+e.toString());
        WebDriverWait wait = WAIT.get();
        wait.until(ExpectedConditions.visibilityOf(e));
      //  wait.until(ExpectedConditions.elementToBeClickable(e));
    }

    public void click(MobileElement e)
    {
        waitforVisibility(e);
        e.click();
        log("Click on  "+e.toString());
    }

    public void sendKeys(MobileElement e , String txt)
    {
        waitforVisibility(e);
        e.sendKeys(txt);
        log("send keys :  "+e.toString());
    }
    public void clickAt ( )
    {
        TouchAction touchAction = new TouchAction(ad);
        touchAction.tap(PointOption.point(648, 1243)).perform();
        //(new TouchAction(ad)).tap.perform();
    }
    public void refresh()
    {
        ad.navigate().refresh();
    }

    public String getAttribute(MobileElement e , String attribute)
    {
        waitforVisibility(e);
        return e.getAttribute(attribute);
    }

    public void pressEnter()
    {
        ((AndroidDriver<MobileElement>)ad).pressKey(new KeyEvent(AndroidKey.ENTER));
        log("Press enter.. ");
    }


    public MobileElement scrollToElement() {
        ad.context("WEBVIEW");

        return (MobileElement)((FindsByAndroidUIAutomator) ad).findElementByAndroidUIAutomator(
         "new UiScrollable(new UiSelector()).scrollIntoView("
                                     + "new UiSelector().textMatches(\"Australia + 61\n\"));");
//
        //  "new UiSelector().textMatches(\"Australia + 61\")");
        // "new UiScrollable(new UiSelector().resourceId(\"country_bottom_sheet_container_signin\")).scrollToBeginning(50)");

//
//        return (MobileElement) ((FindsByAndroidUIAutomator)ad).findElementByAndroidUIAutomator(




    }

    public void scroll(MobileElement element){
//        TouchActions action = new TouchActions(ad);
//        action.scroll(element, 10, 100);
//        action.perform();

    }
    public void scroll(String direction, int duration) {
        Double screenHeightStart,screenHeightEnd;
        Dimension dimensions = ad.manage().window().getSize();

        screenHeightStart = dimensions.getHeight() * 0.5;
        screenHeightEnd = dimensions.getHeight() * 0.2;

        int scrollStart = screenHeightStart.intValue();
        int scrollEnd = screenHeightEnd.intValue();
        if (direction == "UP") {
            //ad.(0, scrollEnd, 0, scrollStart, duration);
        } else if (direction == "DOWN") {
          //  ad.swipe(0, scrollStart, 0, scrollEnd, duration);
        }
    }

    public void scrollVertical()
    {
        try {
             ad.findElements(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector()).setAsVerticalList().flingToEnd(10);"));
        } catch (Exception e) {
            // ignore
        }
    }


    public void iOSScrollToElement() {
        RemoteWebElement element = (RemoteWebElement)ad.findElement(By.name("test-ADD TO CART"));
        String elementID = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementID);
//	  scrollObject.put("direction", "down");
//	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
//	  scrollObject.put("name", "test-ADD TO CART");
        scrollObject.put("toVisible", "sdfnjksdnfkld");
        ad.executeScript("mobile:scroll", scrollObject);
    }

    public AppiumDriver getDriver() {
        return ad;
    }

    public void waitForVisibility(WebElement e){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOf(e));
        wait.until(ExpectedConditions.elementToBeClickable(e));
    }
    public String getText(MobileElement e, String msg) {
        String txt = null;
        switch(GlobalParams.getInstance().getPlatform()) {
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
        }
        log(msg + txt);
        ExtentReport.getTest().log(Status.INFO, msg);
        return txt;
    }

    public boolean find(final By element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(ad, timeout);
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver.findElement(element).isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }
    public void swipe(int startX, int startY, int endX, int endY, int millis)
            throws InterruptedException {
        TouchAction t = new TouchAction(ad);
        t.press(point(startX, startY)).waitAction(waitOptions(ofMillis(millis))).moveTo(point(endX, endY)).release()
                .perform();
    }
    public static void log(String message)
    {
        TestUtil.getInstance().log().info("Thread id "
                +Thread.currentThread().getId()
                +" "
                +message);
    }
    protected final <T extends BasePage, U> T launchPage(Class<T> t)
    {
        try
        {
           // T page = t.newInstance();
            return t.getDeclaredConstructor(WebDriver.class).newInstance(this.ad);
            //return page;
        }
        catch (Exception e)
        {
        }
        return null;
    }
}
