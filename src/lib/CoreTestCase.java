package lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumUrl = "http://127.0.0.1:4723/wd/hub";

    // private static String PathApk = "/Users/evgeniy_g/coures/project/JavaAppiumMac/apks/org.wikipedia.apk";
    private static String PathApk = "D:\\[courses]\\project\\\\JavaAppiumMac\\apks\\org.wikipedia.apk";

    @Override
    protected void setUp() throws Exception {
        super.setUp();  // используем метод из TestCase
        DesiredCapabilities сapabilities = new DesiredCapabilities();
        сapabilities.setCapability("platformName", "Android");
        сapabilities.setCapability("deviceName", "AndroidTestDevice");
        сapabilities.setCapability("platformVersion", "8.0");
        сapabilities.setCapability("automationName", "Appium");
        сapabilities.setCapability("appPackage", "org.wikipedia");
        сapabilities.setCapability("appActivity", ".main.MainActivity");
        сapabilities.setCapability("app", PathApk);
        driver = new AndroidDriver(new URL(AppiumUrl), сapabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(seconds);
    }
}