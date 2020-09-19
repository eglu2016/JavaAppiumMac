import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;

public class StudyTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app",
                "/Users/evgeniy_g/coures/project/JavaAppiumMac/apks/org.wikipedia.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void FirstTest() throws Exception {
        // click Search Wikipedia input
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                10 );
        // enter Java text in Search... input
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find Search... input",
                10);
        // check appears text 'Object-oriented programming language'
        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot displayed result search",
                20);

        Thread.sleep(5000);
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private boolean waitForElementIsNotPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.clear();
        return element;
    }


}
