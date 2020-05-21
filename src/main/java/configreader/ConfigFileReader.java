package configreader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    //singleton class pattern - one instance for all

    private final static ConfigFileReader configFileReader = new ConfigFileReader();
    private Properties properties;
    private final String propertyFilePath= "resources//Config.properties";


    private ConfigFileReader()
    {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }
    public static ConfigFileReader getInstance(){
        return configFileReader;
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

    public String getAndroidAutomationName()
    {
        String androidAutomationName = properties.getProperty("androidAutomationName");
        if(androidAutomationName!= null) return androidAutomationName;
        else throw new RuntimeException("android automation name not specified in the Configuration.properties file.");
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

    public String getAppiumUrl() {
        String appiumURL = properties.getProperty("appiumURL");
        if(appiumURL != null) return appiumURL;
        else throw new RuntimeException("appium url not specified in the Configuration.properties file.");
    }



}
