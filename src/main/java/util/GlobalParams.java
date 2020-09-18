package util;

import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;

public class GlobalParams {

    private static ThreadLocal<GlobalParams> GlobalParams =new ThreadLocal<>();

    private static ThreadLocal <String> platform = new ThreadLocal<String>();
    private static ThreadLocal <String> deviceName = new ThreadLocal<String>();
    private static ThreadLocal <String> platformVersion = new ThreadLocal<>();
    private static ThreadLocal <String> udid = new ThreadLocal<>();
    private static ThreadLocal<String> systemPort = new ThreadLocal<String>();
    private static ThreadLocal<String> chromeDriverPort = new ThreadLocal<String>();
    private static ThreadLocal<String> wdaLocalPort = new ThreadLocal<String>();
    private static ThreadLocal<String> webkitDebugProxyPort = new ThreadLocal<String>();


    private GlobalParams(){ }


    public static GlobalParams getInstance() {

        if(GlobalParams.get()==null)
        {
            GlobalParams.set(new GlobalParams());
            TestUtil.getInstance().log().info("Thread id :"+ Thread.currentThread().getId()+
                    "  Launching constructor of global params");
        }
        return GlobalParams.get();
    }

    //for TestNG parameters
    @Parameters({"platformName","platformVersion","deviceName"})
    public void testNGParams(String platformName ,String platformVersion,String deviceName)
    {
        Map<String,String> params = new HashMap<>();
        if(!(platformName.isEmpty() && platformVersion.isEmpty() && deviceName.isEmpty()))
        {
            setPlatformName(platformName);
            setDeviceName(deviceName);
            setPlatformVersion(platformVersion);

        }
        else //use Maven command line arguments or use default
        {
            setPlatformName(System.getProperty("platformName", "Android"));
            setDeviceName(System.getProperty("deviceName", "emulator-5554"));
            setUDID(System.getProperty("udid", "<enter_device_udid_here>>"));

        }
        //return params;

    }

    //set and get platformVersion
    private void setPlatformVersion(String platformVersion2)
    {
        platformVersion.set(platformVersion2);
    }
    public String getPlatformVersion()
    {
        return platformVersion.get();
    }

    //set and get platform name
    private void setPlatformName(String platform2)
    {
        platform.set(platform2);
    }
    public String getPlatform() { return platform.get();    }

    //set and get device name
    private void setDeviceName(String deviceName2) {deviceName.set(deviceName2); }
    public String getDeviceName() { return deviceName.get();}

    //set and get udid
    private void setUDID (String UDID2){
        udid.set(UDID2);
    }
    public String getUDID()
    {
        return udid.get();
    }

}
