
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

/**
 * Простые сценарии в Appium
 */
public class ThirdTaskTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities сapabilities = new DesiredCapabilities();
        сapabilities.setCapability("platformName", "Android");
        сapabilities.setCapability("deviceName", "AndroidTestDevice");
        сapabilities.setCapability("platformVersion", "8.0");
        сapabilities.setCapability("automationName", "Appium");
        сapabilities.setCapability("appPackage", "org.wikipedia");
        сapabilities.setCapability("appActivity", ".main.MainActivity");
        сapabilities.setCapability("app",
                "/Users/evgeniy_g/coures/project/JavaAppiumMac/apks/org.wikipedia.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), сapabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void methodCreationTest() {
        // check 'Search Wikipedia' text in Search input
        WebElement search_element = waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']/*[@class='android.widget.TextView']"),
                "Search input is not found",
                15);
        assertElementHasText(search_element, "Search Wikipedia",
                "Cannot find 'Search Wikipedia' text in Search input");
        // check 'In the news' text in title
        WebElement element = waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_card_header_title']"),
                "Header title is not found");
        assertElementHasText(element, "In the news",
                "Cannot find 'In the news' text in title");
    }

    @Test
    public void cancelSearchTest() {
        // click 'Search Wikipedia' input
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                20);

        // input words 'iata code' in Search... input
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "iata code",
                "Cannot input value in 'Search...' input",
                10);

        // check first result after search
        String actual_value = waitForElementAndGetText(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_redirect'])[1]"),
                "Cannot find first result, after search",
                20
        );
        Assert.assertTrue(
                "First result cannot contains IATA code; Actual value = " + actual_value,
                actual_value.contains("IATA code"));

        // check second result after search
        actual_value = waitForElementAndGetText(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_redirect'])[2]"),
                "Cannot find second result, after search",
                20
        );
        Assert.assertTrue(
                "Second result cannot contains IATA code; Actual value = " + actual_value,
                actual_value.contains("IATA code"));

        // clear Search... input
        waitForElementAndClear(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Cannot find Search... input",
                10
        );

        // click X button
        waitForElementAndClick(
                By.xpath("//*[contains(@resource-id,'search_close_btn')]"),
                "Cannot find X to cancel search",
                10);

        // check X disappeared
        waitForElementNotPresent(
                By.xpath("//*[contains(@resource-id,'search_close_btn')]"),
                "X is still present on page",
                10);
    }

    @Test
    public void checkWorldsInSearchTest() {
        // click 'Search Wikipedia' input
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                20);

        // input word 'Java' in Search... input
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot enter value in 'Search...' input",
                10);
        // get values in results
        List<WebElement> result_items = waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find results on result page",
                20
        );
        for (int i = 0; i < result_items.size() - 1; i++) {
            String actual_title_value = result_items.get(i).findElement(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']")).getText();
            String actual_description_value = result_items.get(i).findElement(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']")).getText();
            Assert.assertTrue("Cannot find word Java in title and description in result page\n AR = " +
                            actual_title_value + " / " + actual_description_value,
                    actual_title_value.contains("Java") || actual_description_value.contains("Java"));
        }
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    private String waitForElementAndGetText(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        return element.getText();
    }

    private void assertElementHasText(WebElement element, String expected_text, String error_message) {
        String actual_text = element.getText();
        Assert.assertEquals(error_message, actual_text, expected_text);
    }

    private List<WebElement> waitForElementsPresent(By by, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
}