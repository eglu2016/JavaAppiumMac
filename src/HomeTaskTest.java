
import Tools.AppiumWebDriver;
import Tools.Utils;
import Tools.WaitingElements;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HomeTaskTest {

    private AppiumDriver driver;
    private WaitingElements waitElement;
    private Utils utils;

    String search_wikipedia_input_locator = "//*[contains(@resource-id,'search_container')]";
    String search_input_locator = "//*[contains(@resource-id,'search_src_text')]";
    String search_close_button_locator = "//*[contains(@resource-id,'search_close_btn')]";
    String title_text_open_page_locator = "//*[contains(@resource-id,'view_page_title_text')]";
    String more_options_item_locator = "//android.widget.ImageView[@content-desc='More options']";
    String add_to_reading_list_locator = "//*[@text='Add to reading list']";
    String got_it_button_locator = "//*[@resource-id='org.wikipedia:id/onboarding_button']";
    String name_of_list_input_locator = "//*[@resource-id='org.wikipedia:id/text_input']";
    String ok_button_locator = "//*[@text='OK']";
    String navigate_up_button_locator = "//android.widget.ImageButton[@content-desc='Navigate up']";
    String my_list_button_locator = "//android.widget.FrameLayout[@content-desc='My lists']";
    String title_list_of_results_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_title']";

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

    /**
     * Простые сценарии в Appium
     * Создание метода
     */
    @Test
    public void testMethodCreation() {
        String search_wikipedia_input_locator = "//*[contains(@resource-id,'search_container')]" +
                "/*[@class='android.widget.TextView']";
        // check 'Search Wikipedia' text in Search input
        utils.assertElementHasText(By.xpath(search_wikipedia_input_locator),
                "Search Wikipedia",
                "Cannot find 'Search Wikipedia' text in Search input");
        // check 'In the news' text in title
        utils.assertElementHasText(By.xpath("//*[contains(@resource-id,'view_card_header_title')]"),
                "In the news",
                "Cannot find 'In the news' text in title");
    }

    /**
     * Простые сценарии в Appium
     * Тест: отмена поиска
     */
    @Test
    public void testCancelSearch() {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find Search Wikipedia input",
                20);

        // input words 'iata code' in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                "iata code",
                "Cannot input value in 'Search...' input",
                10);

        // check first result after search
        String actual_value = waitElement.waitForElementAndGetText(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_redirect'])[1]"),
                "Cannot find first result, after search",
                20
        );
        Assert.assertTrue(
                "First result cannot contains IATA code; Actual value = " + actual_value,
                actual_value.contains("IATA code"));

        // check second result after search
        actual_value = waitElement.waitForElementAndGetText(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_redirect'])[2]"),
                "Cannot find second result, after search",
                20
        );
        // check
        Assert.assertTrue(
                "Second result cannot contains IATA code; Actual value = " + actual_value,
                actual_value.contains("IATA code"));
        // clear Search... input
        waitElement.waitForElementAndClear(
                By.xpath(search_input_locator),
                "Cannot find Search... input",
                10
        );
        // click X button
        waitElement.waitForElementAndClick(
                By.xpath(search_close_button_locator),
                "Cannot find X to cancel search",
                10);
        // check X disappeared
        waitElement.waitForElementIsNotPresent(
                By.xpath(search_close_button_locator),
                "X is still present on page",
                10);
    }

    /**
     * Простые сценарии в Appium
     * Тест: проверка слов в поиске
     */
    @Test
    public void testCheckWorldsInSearch() {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find Search Wikipedia input",
                20);

        // input word 'Java' in Search... input
        waitElement.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                "Java",
                "Cannot enter value in 'Search...' input",
                10);
        // get values in results
        List<WebElement> result_items = waitElement.waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Cannot find results on result page",
                20
        );

        String list_item_title_locator = "//*[contains(@resource-id,'page_list_item_title')]";
        String list_item_description_locator = "//*[contains(@resource-id, 'page_list_item_description')]";
        for (int i = 0; i < result_items.size() - 1; i++) {
            String actual_title_value = result_items.get(i).findElement(
                    By.xpath(list_item_title_locator)).getText();
            String actual_description_value = result_items.get(i).findElement(
                    By.xpath(list_item_description_locator)).getText();
            Assert.assertTrue("Cannot find word Java in title and description in result page\n AR = " +
                            actual_title_value + " / " + actual_description_value,
                    actual_title_value.contains("Java") || actual_description_value.contains("Java"));
        }
    }

    /**
     * Сложные тесты
     * Тест: сохранение двух статей
     */
    @Test
    public void testSavedOfTwoArticles() {

        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        // enter text in Search... input
        String search_line = "Android";
        waitElement.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input and enter " + search_line,
                10);

        // click by text
        String search_text_first_article = "Android (operating system)";
        waitElement.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id,'search_results_list')]//*[@text='" + search_text_first_article + "']"),
                "Cannot click by text: " + search_text_first_article + " in search results list",
                20);

        // check title first article
        utils.assertElementHasText(
                By.xpath(title_text_open_page_locator),
                search_text_first_article,
                "Wrong text title opened first article");

        // click 'More options' element
        waitElement.waitForElementAndClick(
                By.xpath(more_options_item_locator),
                "Cannot find 'More options' button",
                5);

        // click 'Add to reading list' item
        waitElement.waitForElementAndClick(
                By.xpath(add_to_reading_list_locator),
                "Cannot find 'Add to reading list' item",
                5);

        // click 'Got It'
        waitElement.waitForElementAndClick(
                By.xpath(got_it_button_locator),
                "Cannot find 'Got It' button",
                5);

        // clear Name of List input
        waitElement.waitForElementAndClear(
                By.xpath(name_of_list_input_locator),
                "Cannot find 'Name of List' input",
                10);

        // enter text in Name of List input
        String name_of_folder = "os_lists";
        waitElement.waitForElementAndSendKeys(
                By.xpath(name_of_list_input_locator),
                name_of_folder,
                "Cannot put text in 'Name of List' input " + name_of_folder,
                5);

        // click 'OK'
        waitElement.waitForElementAndClick(
                By.xpath(ok_button_locator),
                "Cannot press 'OK' button",
                5);

        // click 'Navigate Up' button
        waitElement.waitForElementAndClick(
                By.xpath(navigate_up_button_locator),
                "Cannot close article, cannot find X link",
                5);

        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input after click X button",
                10);

        // enter text in Search... input
        search_line = "Microsoft Windows";
        waitElement.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input and enter " + search_line,
                10);

        // click by text for open second article
        String search_text_second_article = "Microsoft Windows";
        waitElement.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id,'search_results_list')]//*[@text='" + search_text_second_article + "']"),
                "Cannot click by text: " + search_text_second_article + " in search results list",
                20);

        // check title second article
        utils.assertElementHasText(
                By.xpath(title_text_open_page_locator),
                search_text_second_article,
                "Wrong text title opened second article");

        // click 'More options' element
        waitElement.waitForElementAndClick(
                By.xpath(more_options_item_locator),
                "Cannot find 'More options' button for second article",
                5);

        // click 'Add to reading list' item
        waitElement.waitForElementAndClick(
                By.xpath(add_to_reading_list_locator),
                "Cannot find 'Add to reading list' item for second article",
                5);

        // click 'os_lists' element
        waitElement.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find '" + name_of_folder + "'",
                10);

        // click 'Navigate Up' button for close second article
        waitElement.waitForElementAndClick(
                By.xpath(navigate_up_button_locator),
                "Cannot close second article, cannot find X link",
                5);

        // click My List button
        waitElement.waitForElementAndClick(
                By.xpath(my_list_button_locator),
                "Cannot find navigation 'My lists' button",
                10);

        // click created folder
        waitElement.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder " + name_of_folder,
                5);

        // get list of result in name folder
        List<WebElement> result_list_elements = waitElement.waitForElementsPresent(
                By.xpath(title_list_of_results_locator),
                "Cannot find elements in list of results " + name_of_folder,
                10);

        // check amount element in name folder
        Assert.assertTrue(
                "Wrong amount elements in " + name_of_folder,
                result_list_elements.size() == 2);

        // get webElement for delete
        WebElement element_for_delete = null;
        for (int i = 0; i < result_list_elements.size(); i++) {
            String title_name = result_list_elements.get(i).getText();
            if (title_name.equals(search_text_first_article)) {
                element_for_delete = result_list_elements.get(i);
            }
        }

        // check webElement is not null
        Assert.assertNotNull(
                "Cannot initialized webElement for delete",
                element_for_delete);

        // delete element with text first article
        utils.swipeElementToLeft(element_for_delete);

        // open element in list, when not deleted
        waitElement.waitForElementAndClick(
                By.xpath(title_list_of_results_locator),
                "Cannot find element for open in list of result " + name_of_folder,
                10);

        // check title article
        utils.assertElementHasText(
                By.xpath(title_text_open_page_locator),
                search_text_second_article,
                "Wrong title in opened article");
    }

    /**
     * Сложные тесты
     * Тест: assert title
     */
    @Test
    public void testAssertTitle() {
        // click 'Search Wikipedia' input
        waitElement.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        // enter text in Search... input
        String search_line = "Android";
        waitElement.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input and enter " + search_line,
                10);

        // click by text
        String search_text_article = "Android (operating system)";
        waitElement.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id,'search_results_list')]//*[@text='" + search_text_article + "']"),
                "Cannot click by text: " + search_text_article + " in search results list",
                20);

        // waitElement.waitForElementPresent(By.xpath(title_text_open_page_locator), "", 20);
        // check appears title
        utils.assertElementPresent(By.xpath(title_text_open_page_locator),
                "Cannot find title: " + search_text_article);
    }
}