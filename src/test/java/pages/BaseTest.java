package pages;

import base.BasePage;
import driverfactory.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import util.GlobalParams;
import util.ServerManager;
import util.TestUtil;
import util.VideoManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

//use input stream as file is in withing package level
public class BaseTest {

    private static ThreadLocal<AppiumDriver> ad = new ThreadLocal<>();
    private static ServerManager serverManager;
    private VideoManager videoManager = null;
    protected static HashMap<String, String> Strings;

    private void setDriver(AppiumDriver appiumDriver)
    {
        ad.set(appiumDriver);
    }
    public static AppiumDriver getDriver()
    {
        return ad.get();
    }

    @BeforeSuite
    public void beforeSuite() throws  Exception {

        serverManager = new ServerManager();
        serverManager.startServer();
        //serverManager.getServer().clearOutPutStreams();
    }

    @Parameters({"platformName","platformVersion","deviceName"})
    @BeforeTest
    public synchronized void setup(String platformName,String platformVersion ,String deviceName) throws IOException
    {
        InputStream stringsIS=null;
        GlobalParams params = GlobalParams.getInstance();
        params.testNGParams(platformName,platformVersion,deviceName);
        TestUtil testUtils = TestUtil.getInstance();
        GlobalParams.getInstance().testNGParams(platformName,platformVersion,deviceName);

        String strFile = "logs" + File.separator +platformName + "_" + deviceName;
        File logFile = new File(strFile);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        //route logs to separate file for each thread
        ThreadContext.put("ROUTINGKEY", strFile);
        log("Before Test in BaseTest class......");
        log("Log path : " + strFile);

        try
        {

            String xmlFileName="strings/strings.xml";
            stringsIS=getClass().getClassLoader().getResourceAsStream(xmlFileName);
            Strings = testUtils.parseStringXML(stringsIS);

            //will get the relevant mobile driver alongwith all the capabilities set
            MobileDriverFactory.getMobileDriver(platformName);
            setDriver(MobileDriverFactory.getMobileDriverInstance());
        }
        catch(Exception ex)
        {
            System.out.println("Cause is :" +ex.getCause());
            System.out.println("Message is :" +ex.getMessage());
            ex.printStackTrace();

        }
        finally
        {
            if(stringsIS!=null)
            {
                stringsIS.close();
            }
        }
    }

    @BeforeMethod
    public void beforeMethod() {
       log("Before method of basetest ...............");
       this.getVideoManagerInstance().startRecording();

    }

    @AfterMethod
    public synchronized void afterMethod(ITestResult result) throws Exception
    {
        log("after method of basetest...............");
        this.getVideoManagerInstance().stopRecording(result);
        closeApp();

    }

    @AfterTest
    public void teardown() {

        MobileDriverFactory.quit();
    }

    @AfterSuite (alwaysRun = true)
    public void afterSuite() {
        log("In after suite...........");
        log("server value "+serverManager.getServer().toString());
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
            log("Appium server stopped");
        }


    }
    private VideoManager getVideoManagerInstance(){

       if(videoManager==null)
       {
           videoManager= new VideoManager();
       }
        return videoManager;
    }

    public void closeApp() {
        ((InteractsWithApps) ad.get()).closeApp();
        log("Closing app");
    }

    public void launchApp() {
        ((InteractsWithApps) ad.get()).launchApp();
        log("launching app");
    }






    public void log(String message)
    {
        TestUtil.getInstance().log().info("Thread id : "+Thread.currentThread().getId()+" "+ message);
    }

    protected final <T extends BasePage, U> T launchPage(Class<T> t)
    {
        try
        {
            //T page = t.newInstance();
            return t.getDeclaredConstructor(AppiumDriver.class).newInstance(getDriver());
        }
        catch (Exception e)
        {
        }
        return null;
    }
//        }

}
