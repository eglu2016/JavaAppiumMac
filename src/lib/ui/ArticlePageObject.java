package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "//*[contains(@resource-id,'view_page_title_text')]",
            FOOTER_ELEMENT = "//*[@resource-id='org.wikipedia:id/page_external_link']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
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
}
