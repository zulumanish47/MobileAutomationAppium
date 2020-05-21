package driverfactory;

import io.appium.java_client.AppiumDriver;

import java.io.IOException;

public interface MobileDriver {
    AppiumDriver setUpDriver() throws IOException;
}
