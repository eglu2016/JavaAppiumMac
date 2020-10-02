package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "//*[contains(@resource-id,'view_page_title_text')]",
            FOOTER_ELEMENT = "//*[@resource-id='org.wikipedia:id/page_external_link']",
            OPTION_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTION_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "//*[@resource-id='org.wikipedia:id/onboarding_button']",
            MY_LIST_NAME_INPUT = "//*[@resource-id='org.wikipedia:id/text_input']",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.xpath(TITLE),
                 "Cannot find article title on page",
                15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getText();
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT),
                "Cannot find the of article",
                20);
    }

    /**
     * add article in new list (only created)
     * @param name_of_folder
     */
    public void addArticleToMyNewList(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(OPTION_BUTTON),
                "Cannot find and press 'More options' button",
                5);
        this.waitForElementAndClick(
                By.xpath(OPTION_ADD_TO_MY_LIST_BUTTON),
                "Cannot find and press 'Add to reading list' item",
                5);
        // click 'Got It'
        this.waitForElementAndClick(
                By.xpath(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find and press 'Got It' button",
                5);
        // clear Name of List input
        this.waitForElementAndClear(
                By.xpath(MY_LIST_NAME_INPUT),
                "Cannot find and clear 'Name of List' input",
                10);
        // set Learning programing in input
        this.waitForElementAndSendKeys(
                By.xpath(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text in 'Name of List' input",
                5);
        // click 'OK'
        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot find and press 'OK' button",
                5);
    }

    public void addArticleToMyList(String name_of_folder) {
        // click More options
        this.waitForElementAndClick(
                By.xpath(OPTION_BUTTON),
                "Cannot find and press 'More options' button",
                5);
        // click 'Add to reading list'
        this.waitForElementAndClick(
                By.xpath(OPTION_ADD_TO_MY_LIST_BUTTON),
                "Cannot find and press 'Add to reading list' item",
                5);
        // click Name Folder
        this.waitForElementAndClick(
                By.xpath(getFolderXpathByName(name_of_folder)),
                "Cannot find folder by name " + name_of_folder,
                5);
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot find X link and close article",
                5);
    }

    public void assertArticleTitle(String article_title) {
        String actual_title_article = this.getArticleTitle();
        assertEquals(
                "Article title have been wrong value",
                article_title, actual_title_article);
    }
}