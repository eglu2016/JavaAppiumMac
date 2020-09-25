import Tools.AppiumWebDriver;
import Tools.Utils;
import Tools.WaitingElements;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudyTest {

    private AppiumDriver driver;
    private WaitingElements waitElement;
    private Utils utils;

    private String search_wikipedia_input_locator = "//*[contains(@resource-id, 'search_container')]";
    private String search_input_locator = "//*[contains(@resource-id,'search_src_text')]";
    private String empty_results_label_locator = "//*[@text='No results found']";
    private String search_result_locator = "//*[contains(@resource-id,'search_results_list')]" +
            "/*[contains(@resource-id,'page_list_item_container')]";
    private String title_article_locator = "//*[contains(@resource-id, 'view_page_title_text')]";

    @Before
    public void setUp() throws Exception {
        driver = AppiumWebDriver.getInstance();
        waitElement = new WaitingElements();
        utils = new Utils();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testFirst() throws Exception {
        // click Search Wikipedia input
        waitElement.waitForElementAndClick(
                By.xpath("//*[id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);
        // enter Java text in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath("//*[id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find Search... input",
                10);
        // check appears text 'Object-oriented programming language'
        waitElement.waitForElementPresent(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot displayed result search",
                20);
        Thread.sleep(500);
    }

    @Test
    public void testSwipeArticleTitle() throws InterruptedException {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_container')]"),
                "Cannot find Search Wikipedia input",
                10);
        // enter text in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Appium",
                "Cannot find Search... input",
                10);

        // click by text
        waitElement.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Appium']"),
                "Cannot click by text",
                20);
        // check appears title
        waitElement.waitForElementPresent(
                By.xpath(title_article_locator),
                "Cannot find title",
                20);
        // View page in browser
        utils.swipeUpToFindElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_external_link']"),
                "Cannot find the end of the article",
                20
        );
        Thread.sleep(500);
    }

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);
        // enter text in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find Search... input",
                10);
        // check appears text
        waitElement.waitForElementPresent(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot displayed result search",
                20);
        // click by text
        waitElement.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot click by text",
                5);
        // check appears title
        waitElement.waitForElementPresent(
                By.xpath(title_article_locator),
                "Cannot find title",
                20);
        // click More options element
        waitElement.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5);
        // click 'Add to reading list'
        waitElement.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' item",
                5);
        // click 'Got It'
        waitElement.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button']"),
                "Cannot find 'Got It' button",
                5);
        // clear Name of List input
        waitElement.waitForElementAndClear(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                "Cannot find 'Name of List' input",
                10);
        String name_of_folder = "Learning programming";
        // set Learning programing in input
        waitElement.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                name_of_folder,
                "Cannot put text in 'Name of List' input",
                5);
        // click 'OK'
        waitElement.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                5);
        // click 'Navigate Up' button
        waitElement.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                " >>> Cannot close article, cannot find X link",
                5);
        // click My List button
        waitElement.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation 'My lists' button",
                10);
        // click 'Learning programming' folder
        waitElement.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                " >>> Cannot find created folder",
                5);
        // wait appears 'Java (programming language)' title
        waitElement.waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' title",
                10);
        // swipe for delete
        utils.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                ">>> Cannot find saved article"
        );
        // check what deleted article
        waitElement.waitForElementIsNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                ">>> Cannot delete saved article",
                5
        );
        Thread.sleep(1000);
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"),
                "Cannot find Search Wikipedia input",
                10);

        String search_line = "linkin park discography";
        // enter text in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath("//*[contains(@resource-id,'search_src_text')]"),
                search_line,
                "Cannot find Search... input",
                10);
        // wait result search
        waitElement.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                20);
        // check
        int amount_of_search_result = utils.getAmountOfElements(By.xpath(search_result_locator));
        Assert.assertTrue("We found too few results",
                amount_of_search_result > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        String search_line = "xcdafgh";
        // enter text in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input",
                10);
        //
        waitElement.waitForElementPresent(
                By.xpath(empty_results_label_locator),
                " >>> Cannot find empty result label by the request " + search_line,
                15);
        // check
        utils.assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line);
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() throws InterruptedException {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        String search_line = "Java";
        // enter text in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input",
                10);
        // click by text
        waitElement.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by " + search_line,
                20);
        // get article value
        String title_before_rotate = waitElement.waitForElementAndGetText(
                By.xpath(title_article_locator),
                "Cannot find title of article",
                15);
        // rotate screen
        driver.rotate(ScreenOrientation.LANDSCAPE);
        // get article value
        String title_after_rotate = waitElement.waitForElementAndGetText(
                By.xpath(title_article_locator),
                "Cannot find title of article after rotation",
                15);
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotate, title_after_rotate);
        // rotate screen
        driver.rotate(ScreenOrientation.PORTRAIT);
        // get article value
        String title_after_second_rotate = waitElement.waitForElementAndGetText(
                By.xpath(title_article_locator),
                "Cannot find title of article after rotation",
                15);
        // check
        Assert.assertEquals(
                "Article title have been changed after screen rotation (portrait)",
                title_before_rotate, title_after_second_rotate);
        Thread.sleep(5000);
    }
}
