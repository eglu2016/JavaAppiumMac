package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;

/**
 * Класс в котором будут методы для поиска
 */
public class SearchPageObject extends MainPageObject {
    /**
     * константы
     */
    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@resource-id,'search_container')]",
        SEARCH_INPUT = "//*[contains(@resource-id,'search_src_text')]",
        SEARCH_CANCEL_BUTTON = "//*[contains(@resource-id,'search_close_btn')]",
        SEARCH_RESULT_BY_SUBSTRING_TPL =
                "//*[contains(@resource-id,'page_list_item_container')]//*[contains(@text,'{SUBSTRING}')]",
        SEARCH_RESULT_ELEMENT = "//*[contains(@resource-id,'search_results_list')]" +
            "/*[contains(@resource-id,'page_list_item_container')]",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver) {
        // берем драйвер из MainPageObject
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE METHODS */

    /**
     * click 'Search Wikipedia' input and check appears Search input
     */
    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INPUT),
                "Cannot find search input after clicking init search element");
    }

    /**
     * typeSearchLine
     * @param search_line
     */
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line,
                "Cannot find and type search input", 5);
    }

    /**
     * check text in search results
     */
    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring, 15);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.xpath(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisAppear() {
        this.waitForElementIsNotPresent(By.xpath(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.xpath(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button", 5);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),
                "Cannot find and click search result with substring " + substring, 15);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request", 20);
        return  this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result label", 15);
    }

    public void assertThereIsNoResultSearch() {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results");
    }
}