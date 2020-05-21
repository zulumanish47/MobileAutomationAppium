package driverfactory;

import io.appium.java_client.AppiumDriver;

import java.io.IOException;

public final class MobileDriverFactory {

    private MobileDriverFactory(){};

    public static AppiumDriver getMobileDriver(String platform) throws IOException {
        MobileDriver mobileDriver;
        AppiumDriver driver=null;

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
        driver =mobileDriver.setUpDriver();

            return driver;
    }



}
