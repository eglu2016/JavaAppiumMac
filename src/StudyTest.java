import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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
                By.xpath("//*[id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);
        // enter Java text in Search... input
        waitForElementAndSendKeys(
                By.xpath("//*[id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find Search... input",
                10);
        // check appears text 'Object-oriented programming language'
        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot displayed result search",
                20);
        Thread.sleep(500);
    }

    @Test
    public void testSwipeArticleTitle() throws InterruptedException {
        // click 'Search Wikipedia' input
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);
        // enter text in Search... input
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Appium",
                "Cannot find Search... input",
                10);
        // check appears text
        waitForElementPresent(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Appium']"),
                "Cannot displayed result search",
                20);
        // click by text
        waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Appium']"),
                "Cannot click by text",
                5
        );
        // check appears title
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "Cannot find title",
                20
        );
        // View page in browser
        swipeUpToFindElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_external_link']"),
                "Cannot find the end of the article",
                20
        );
        Thread.sleep(500);
    }

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        // click 'Search Wikipedia' input
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);
        // enter text in Search... input
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find Search... input",
                10);
        // check appears text
        waitForElementPresent(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot displayed result search",
                20);
        // click by text
        waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot click by text",
                5
        );
        // check appears title
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "Cannot find title",
                20
        );
        // click More options element
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );
        // click 'Add to reading list'
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' item",
                5
        );
        // click 'Got It'
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button']"),
                "Cannot find 'Got It' button",
                5
        );
        // clear Name of List input
        waitForElementAndClear(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                "Cannot find 'Name of List' input",
                10
        );
        String name_of_folder = "Learning programming";
        // set Learning programing in input
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                name_of_folder,
                "Cannot put text in 'Name of List' input",
                5
        );
        // click 'OK'
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );
        // click 'Navigate Up' button
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                " >>> Cannot close article, cannot find X link",
                5
        );
        // click My List button
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation 'My lists' button",
                10
        );
        // click 'Learning programming' folder
        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                " >>> Cannot find created folder",
                5
        );
        // wait appears 'Java (programming language)' title
        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' title",
                10
        );
        // swipe for delete
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                ">>> Cannot find saved article"
        );
        // check what deleted article
        waitForElementIsNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                ">>> Cannot delete saved article",
                5
        );
        Thread.sleep(1000);
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        // click 'Search Wikipedia' input
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);

        String search_line = "linkin park discography";
        // enter text in Search... input
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@resource-id,'search_src_text')]"),
                search_line,
                "Cannot find Search... input",
                10);

        String search_result_locator = "//*[contains(@resource-id,'search_results_list')]" +
                "/*[contains(@resource-id,'page_list_item_container')]";
        // wait result search
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                20);

        int amount_of_search_result = getAmountOfElements(By.xpath(search_result_locator));
        Assert.assertTrue("We found too few results",
                amount_of_search_result > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        // click 'Search Wikipedia' input
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);

        String search_line = "xcdafgh";
        // enter text in Search... input
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@resource-id,'search_src_text')]"),
                search_line,
                "Cannot find Search... input",
                10);

        String search_result_locator = "//*[contains(@resource-id,'search_results_list')]" +
                "/*[contains(@resource-id,'page_list_item_container')]";
        String empty_results_label_locator = "//*[@text()='No results found']";
        waitForElementPresent(
                By.xpath(empty_results_label_locator),
                " >>> Cannot find empty result label by the request " + search_line,
                15
        );
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

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipe) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipe) {
                waitForElementPresent(by,
                        "Cannot find element by swiping up" + "\n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        // получаем левую точку по оси Х
        int left_x = element.getLocation().getX();
        // получаем правую точку по оси X
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }
}
