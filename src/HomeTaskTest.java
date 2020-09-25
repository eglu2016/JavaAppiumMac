
import Tools.AppiumWebDriver;
import Tools.Utils;
import Tools.WaitingElements;
import org.junit.Assert;
import org.openqa.selenium.By;
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


}