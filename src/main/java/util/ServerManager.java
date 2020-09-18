package util;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class ServerManager {
    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();
    private TestUtil utils = TestUtil.getInstance();

    public ServerManager()
    {
        log("Launching constructor of "+ this.getClass().getSimpleName());
    }

    public AppiumDriverLocalService getServer(){
        return server.get();
    }

    private void setServer(AppiumDriverLocalService server2){server.set(server2);}

    public void startServer() throws Exception{

       ThreadContext.put("ROUTINGKEY","ServerLogs");
       if(!checkIfAppiumServerIsRunnning(4723))
        {
           log("Starting appium server.........");

            AppiumDriverLocalService server2 = WindowsGetAppiumService();
            server2.start();
            if(!server2.isRunning())
            {
                utils.log().fatal("Appium server not started. ABORT!!!");
                throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
            }
            server2.clearOutPutStreams();
            server.set(server2);
            log("Appium server started..........");

        } else {
           log("Appium server already running");
        }


    }

    public void stopserver(){

         this.getServer().stop();
    }

    private boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
        boolean isAppiumServerRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {

            isAppiumServerRunning = true;
        } finally {
            socket = null;
        }
        return isAppiumServerRunning;
    }

    private AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    private AppiumDriverLocalService WindowsGetAppiumService() {
        GlobalParams params = GlobalParams.getInstance();
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withLogFile(new File("ServerLogs/server.log")));
                //.withLogFile(new File(params.getPlatform() + "_"
        //        + params.getDeviceName() + File.separator + "Server.log")));
    }

    private AppiumDriverLocalService MacGetAppiumService() {
        GlobalParams params = GlobalParams.getInstance();
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH", "/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/bin:/Users/Om/Library/Android/sdk/tools:/Users/Om/Library/Android/sdk/platform-tools:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin" + System.getenv("PATH"));
        environment.put("ANDROID_HOME", "/Users/Om/Library/Android/sdk");
        environment.put("JAVA_HOME", "/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
                .withLogFile(new File(params.getPlatform() + "_"
                        + params.getDeviceName() + File.separator + "Server.log")));
    }

    public AppiumDriverLocalService getAppiumService() {
        HashMap<String, String> environment = new HashMap<String, String>();
//        environment.put("PATH", "/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/bin:" +
//                "/Users/Om/Library/Android/sdk/tools:" +
//                "/Users/Om/Library/Android/sdk/platform-tools:" +
//                "/usr/local/bin:" +
//                "/usr/bin:" +
//                "/bin:" +
//                "/usr/sbin:" +
//                "/sbin" + System.getenv("PATH"));
        environment.put("ANDROID_HOME", "C:\\Users\\Public\\AppData\\Local\\Android\\Sdk");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                .withAppiumJS(new File("C:\\Users\\manish meera\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\lib\\main.js"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
                .withLogFile(new File("./test-output/ServerLogs/server.log")));
    }


    public void log(String message)
    {
        TestUtil.getInstance().log().info("Thread id : "+Thread.currentThread().getId()+" "+ message);
    }
}
