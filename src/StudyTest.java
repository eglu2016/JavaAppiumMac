import Tools.AppiumWebDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class StudyTest {

    private AppiumDriver driver;

    private String search_wikipedia_input_locator = "//*[contains(@resource-id, 'search_container')]";
    private String search_input_locator = "//*[contains(@resource-id,'search_src_text')]";
    private String empty_results_label_locator = "//*[@text='No results found']";
    private String search_result_locator = "//*[contains(@resource-id,'search_results_list')]" +
            "/*[contains(@resource-id,'page_list_item_container')]";
    private String title_article_locator = "//*[contains(@resource-id, 'view_page_title_text')]";

    @Before
    public void setUp() throws Exception {
        driver = AppiumWebDriver.getInstance();
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
                By.xpath("//*[contains(@resource-id, 'search_container')]"),
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
                5);
        // check appears title
        waitForElementPresent(
                By.xpath(title_article_locator),
                "Cannot find title",
                20);
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
                5);
        // check appears title
        waitForElementPresent(
                By.xpath(title_article_locator),
                "Cannot find title",
                20);
        // click More options element
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5);
        // click 'Add to reading list'
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' item",
                5);
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
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        String search_line = "xcdafgh";
        // enter text in Search... input
        waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input",
                10);

        //
        waitForElementPresent(
                By.xpath(empty_results_label_locator),
                " >>> Cannot find empty result label by the request " + search_line,
                15);
        //
        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line);
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() throws InterruptedException {
        // click 'Search Wikipedia' input
        waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        String search_line = "Java";
        // enter text in Search... input
        waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input",
                10);
        // click by text
        waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by " + search_line,
                20);
        // get article value
        String title_before_rotate = waitForElementAndGetValue(
                By.xpath(title_article_locator),
                "Cannot find title of article",
                15);
        // rotate screen
        driver.rotate(ScreenOrientation.LANDSCAPE);
        // get article value
        String title_after_rotate = waitForElementAndGetValue(
                By.xpath(title_article_locator),
                "Cannot find title of article after rotation",
                15);
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotate, title_after_rotate);
        // rotate screen
        driver.rotate(ScreenOrientation.PORTRAIT);
        // get article value
        String title_after_second_rotate = waitForElementAndGetValue(
                By.xpath(title_article_locator),
                "Cannot find title of article after rotation",
                15);
        // check
        Assert.assertEquals(
                "Article title have been changed after screen rotation (portrait)",
                title_before_rotate, title_after_second_rotate);
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

    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element " + by.toString() + " supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeOutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSecond);
        return element.getAttribute(attribute);
    }

    private String waitForElementAndGetValue(By by, String error_message, long timeOutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSecond);
        return element.getText();
    }
}
