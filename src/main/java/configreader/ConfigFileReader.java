package configreader;
import util.TestUtil;

import java.io.*;
import java.util.Properties;

public class ConfigFileReader {

    //singleton class pattern - one instance for all

    private final static ConfigFileReader configFileReader = new ConfigFileReader();
    private Properties properties;
    private TestUtil testutils;
    private final String propertyFilePath= "\\config.properties";


    private ConfigFileReader()
    {
        //BufferedReader reader ;

        InputStream reader;
        TestUtil.getInstance().log().info("Thread id :"+ Thread.currentThread().getId()+
                "  Launching constructor of configfilereader");
        try {
          //  C:\Users\manish meera\Documents\workspace_sais\MobileAutomationAppium\src\main\resources
            //reader = new BufferedReader((getClass().getClassLoader().getResourceAsStream(propertyFilePath)));
           reader = getClass().getClassLoader().getResourceAsStream(propertyFilePath);
            properties = new Properties();
            try {
                testutils.getInstance().log().info("Thread id : " +Thread.currentThread().getId()+": Load property file......");
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){//FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }

    }
    public static ConfigFileReader getInstance(){

        return configFileReader;
    }

    public String getAppiumUrl() {
        String appiumURL = properties.getProperty("appiumURL");
        if(appiumURL != null) return appiumURL;
        else throw new RuntimeException("appium url not specified in the Configuration.properties file.");
    }
    public String getAndroidAutomationName()
    {
        String androidAutomationName = properties.getProperty("androidAutomationName");
        if(androidAutomationName!= null) return androidAutomationName;
        else throw new RuntimeException("android automation name not specified in the Configuration.properties file.");
    }

    public String getAndroidAppPackage(){
        String androidAppPackage = properties.getProperty("androidAppPackage");
        if(androidAppPackage!= null) return androidAppPackage;
        else throw new RuntimeException("android app package not specified in the Configuration.properties file.");
    }

    public String getAndroidAppActivity()
    {
        String androidAppActivity = properties.getProperty("androidAppActivity");
        if(androidAppActivity!= null) return androidAppActivity;
        else throw new RuntimeException("android app activity not specified in the Configuration.properties file.");
    }


    public String getAndroidAppLocation(){
        String androidAppLocation = properties.getProperty("androidAppLocation");
        if(androidAppLocation!= null) return androidAppLocation;
        else throw new RuntimeException("android app location not specified in the Configuration.properties file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
    }





}
