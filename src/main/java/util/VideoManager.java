package util;

import driverfactory.MobileDriverFactory;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class VideoManager {
    public VideoManager() {
        TestUtil.getInstance().log().info("Thread id :" + Thread.currentThread().getId() +
                "  Launching constructor of video manager");
    }

    public void startRecording() {
        ((CanRecordScreen) MobileDriverFactory.getMobileDriverInstance()).startRecordingScreen();
    }

    public void stopRecording(ITestResult result) throws IOException {
        String media = ((CanRecordScreen) MobileDriverFactory.getMobileDriverInstance()).stopRecordingScreen();


        //For TestNG xml
        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();


        String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
                + File.separator + TestUtil.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();

        File videoDir = new File(dirPath);

        synchronized (videoDir) {
            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }
        }
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
            stream.write(Base64.decodeBase64(media));
            stream.close();
            TestUtil.getInstance().log().info("video path: " + videoDir + File.separator + result.getName() + ".mp4");
        } catch (Exception e) {
            TestUtil.getInstance().log().error("error during video capture" + e.toString());
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }


}
