import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.junit.Test;

/*
 * Study test
 */
public class StudyTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    private String search_wikipedia_input_locator = "//*[contains(@resource-id, 'search_container')]";
    private String search_input_locator = "//*[contains(@resource-id,'search_src_text')]";

    private String empty_results_label_locator = "//*[@text='No results found']";
    private String search_result_locator = "//*[contains(@resource-id,'search_results_list')]" +
            "/*[contains(@resource-id,'page_list_item_container')]";
    private String title_article_locator = "//*[contains(@resource-id, 'view_page_title_text')]";
    private String search_close_button_locator = "//*[contains(@resource-id,'search_close_btn')]";

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisAppear();
    }

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals("We see unexpected title!",
                "Java (programming language)",
                article_title);
    }

    @Test
    public void testSwipeArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyList();

        MyListPageObject MyListPageObject = new MyListPageObject(driver);
        MyListPageObject.openFolderByName(name_of_folder);
        MyListPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        // click 'Search Wikipedia' input
        MainPageObject.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find Search Wikipedia input",
                10);

        String search_line = "linkin park discography";
        // enter text in Search... input
        MainPageObject.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find Search... input",
                10);
        // wait result search
        MainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                20);
        // check
        int amount_of_search_result = MainPageObject.getAmountOfElements(By.xpath(search_result_locator));
        Assert.assertTrue("We found too few results",
                amount_of_search_result > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        // click 'Search Wikipedia' input
        MainPageObject.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        String search_line = "xcdafgh";
        // enter text in Search... input
        MainPageObject.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input",
                10);
        //
        MainPageObject.waitForElementPresent(
                By.xpath(empty_results_label_locator),
                " >>> Cannot find empty result label by the request " + search_line,
                15);
        // check
        MainPageObject.assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line);
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() throws InterruptedException {
        // click 'Search Wikipedia' input
        MainPageObject.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find 'Search Wikipedia' input",
                10);

        String search_line = "Java";
        // enter text in Search... input
        MainPageObject.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                search_line,
                "Cannot find 'Search...' input",
                10);
        // click by text
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by " + search_line,
                20);
        // get article value
        String title_before_rotate = MainPageObject.waitForElementAndGetText(
                By.xpath(title_article_locator),
                "Cannot find title of article",
                15);
        // rotate screen
        driver.rotate(ScreenOrientation.LANDSCAPE);
        // get article value
        String title_after_rotate = MainPageObject.waitForElementAndGetText(
                By.xpath(title_article_locator),
                "Cannot find title of article after rotation",
                15);
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotate, title_after_rotate);
        // rotate screen
        driver.rotate(ScreenOrientation.PORTRAIT);
        // get article value
        String title_after_second_rotate = MainPageObject.waitForElementAndGetText(
                By.xpath(title_article_locator),
                "Cannot find title of article after rotation",
                15);
        // check
        Assert.assertEquals(
                "Article title have been changed after screen rotation (portrait)",
                title_before_rotate, title_after_second_rotate);
        Thread.sleep(500);
    }

    @Test
    public void testCheckSearchArticleInBackground() throws InterruptedException {
        // click 'Search Wikipedia' input
        MainPageObject.waitForElementAndClick(
                By.xpath(search_wikipedia_input_locator),
                "Cannot find Search Wikipedia input",
                10);
        // enter text in Search... input
        MainPageObject.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                "Java",
                "Cannot find Search... input",
                10);
        /* check appears text */
        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@resource-id,'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot displayed result search",
                20);
        // background
        driver.runAppInBackground(2);
        // check appears text
        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@resource-id,'search_results_list')]//*[@text='Java (programming language)']"),
                "Cannot displayed result search after background",
                20);
        Thread.sleep(3000);
    }
}