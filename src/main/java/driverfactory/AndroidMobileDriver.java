package driverfactory;

import configreader.ConfigFileReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.GlobalParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AndroidMobileDriver implements MobileDriver
{
    private static AndroidDriver<AndroidElement> ad;

    @Override
    public AppiumDriver setUpDriver() throws IOException
    {
        ConfigFileReader conf = ConfigFileReader.getInstance();

        String deviceName=GlobalParams.getInstance().getDeviceName();
        String platformName= GlobalParams.getInstance().getPlatform();
        String platformVersion=GlobalParams.getInstance().getPlatformVersion();

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        dc.setCapability("avd", "Pixel_XL_API_27");
        dc.setCapability("platformName", platformName);
        dc.setCapability("platformVersion",platformVersion);
        //URL appURL =getClass().getClassLoader().getResourceAsStream(conf.getAndroidAppLocation());
       //    dc.setCapability("app", appURL);
        dc.setCapability("appPackage", conf.getAndroidAppPackage());
        dc.setCapability("appActivity", conf.getAndroidAppActivity());
        dc.setCapability("NoResetApp","false");
        dc.setCapability("androidInstallTimeout", 50000);
        dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 500);
        dc.setCapability("automationName", conf.getAndroidAutomationName());


        URL url = new URL(conf.getAppiumUrl());

        // dc.setCapability(CapabilityType.PLATFORM_NAME,"android");
        //parent class of android and ios driver
        // ad = new AppiumDriver<MobileElement>(url,dc);

        //specific driver of android
        ad = new AndroidDriver<AndroidElement>(url, dc);
        // ad = new IOSDriver<MobileElement>(url,dc);
        return ad;
    }
}
