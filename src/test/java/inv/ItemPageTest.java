package inv;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import inv.core.BaseAPITest;
import inv.ui.ItemPage;
import inv.ui.LoginPage;
import org.junit.jupiter.api.*;

public class ItemPageTest extends BaseAPITest {
    private static Browser browser = null;
    private Page page;
    private final String loginButtonSelector = "input.selenium-submit-button";
    private final String userPanelSelector = "div.userpanel-header";


    @BeforeAll
    static void beforeAll() {
        browser = Playwright.create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(System.getProperty("headless", "true"))));
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
    void canCreateNewItem(){
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
    void canNavigateToItemPage(){
        LoginPage loginPage = new LoginPage(page);
        ItemPage itemPage = new ItemPage(page);
        loginPage.login();
        itemPage.goTo();
        Assertions.assertEquals("Артикули", itemPage.getHeadline());
    }
}
