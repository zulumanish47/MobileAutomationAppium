package driverfactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;

public final class MobileDriverFactory {

    private static final ThreadLocal<AppiumDriver<MobileElement>> driver = new ThreadLocal<>();
    private MobileDriverFactory(){};

    public static void getMobileDriver(String platform) throws IOException {
        MobileDriver mobileDriver;

       // AppiumDriver driver=null;

        switch (platform)
        {
            case "Android":
                mobileDriver = new AndroidMobileDriver();
                break;

            case "IOS":
                mobileDriver = new IOSMobileDriver();
                break;

            default:
                mobileDriver =null;
        }
        driver.set(mobileDriver.setUpDriver());

           // return driver;
    }

    public static AppiumDriver getMobileDriverInstance(){
        return driver.get();
    }

    public static void quit(){
        driver.get().quit();
    }



}
