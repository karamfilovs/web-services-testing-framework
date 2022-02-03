package inv;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import inv.core.BaseAPITest;
import inv.dto.Item;
import inv.ui.ItemPage;
import inv.ui.LoginPage;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ItemPageTest extends BaseAPITest {
    private static Browser browser = null;
    private Page page;
    private static boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));


    @BeforeAll
    static void beforeAll() {
        browser = Playwright.create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
    }


    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test:" + testInfo.getDisplayName());
        //Creates new page instance
        page = browser.newPage();
        //Navigates to (Login page) in this case
        page.navigate("https://st2016.inv.bg/login");
    }

    @Test
    @Tag("ui")
    @DisplayName("Can create new item")
    void canCreateNewItem() {
        LoginPage loginPage = new LoginPage(page);
        ItemPage itemPage = new ItemPage(page);
        loginPage.login();
        itemPage.goTo();
        String message = itemPage.createItem("UI Created Item");
        Assertions.assertEquals("Артикулът е добавен успешно.", message.replace("\u00a0", ""));
    }

    @Test
    @Tag("ui")
    @DisplayName("Can navigate to item page")
    void canNavigateToItemPage() {
        LoginPage loginPage = new LoginPage(page);
        ItemPage itemPage = new ItemPage(page);
        loginPage.login();
        itemPage.goTo();
        Assertions.assertEquals("Артикули", itemPage.getHeadline());
    }

    @ParameterizedTest
    @DisplayName("Can search for item")
    @ValueSource(strings = {"име на артикул", "item_name", "search_test"})
    @Tag("ui")
    @Tag("positive")
    void canSearchForExistingItems(String name) {
        //Change something
        //Clean all existing items
        api.itemAPI().deleteAll();
        //Create new item to search for
        Item item = new Item(name, 1, "кг.", 10.0, "EUR");
        Response resp = api.itemAPI().createItem(item);
        Assertions.assertEquals(201, resp.statusCode());
        LoginPage loginPage = new LoginPage(page);
        loginPage.login();
        //Navigates to Item page
        page.click("#tabs_objects");
        //Expand search form
        page.click("#searchbtn");
        //Enter search criteria
        page.fill("input[name='nm']", name);
        //Trigger search
        page.click("input[name='s']");
        //Check the search result output
        String searchResult = page.textContent("a.faktura_id");
        Assertions.assertTrue(searchResult.contains(name));
    }
}
