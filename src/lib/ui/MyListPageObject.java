package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends  MainPageObject {

    public static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TEMPLATE = "//*[@text='{TITLE}']";

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedXpathByArticle(String article_title) {
        return ARTICLE_BY_TITLE_TEMPLATE.replace("{TITLE}", article_title);
    }

    public void openFolderByName(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(getFolderXpathByName(name_of_folder)),
                "Cannot find folder by name " + name_of_folder,
                5); }

    public void waitForArticleToAppearByTitle(String article_title) {
        this.waitForElementPresent(
                By.xpath(getSavedXpathByArticle(article_title)),
                "Cannot find saved article by title " + article_title,
                15);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        this.waitForElementIsNotPresent(
                By.xpath(getSavedXpathByArticle(article_title)),
                "Saved article still present with title " + article_title,
                15);
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(getSavedXpathByArticle(article_title)),
                "Cannot find saved article by title " + article_title);
        waitForArticleToDisappearByTitle(article_title);
    }
}