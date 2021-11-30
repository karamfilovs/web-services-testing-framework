import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import inv.BaseAPITest;
import inv.dto.Item;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LoginPageTest extends BaseAPITest {
    private static Browser browser = null;
    private Page page;
    private final String loginButtonSelector = "input.selenium-submit-button";
    private final String userPanelSelector = "div.userpanel-header";


    @BeforeAll
    static void beforeAll() {
        browser = Playwright.create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(Boolean.valueOf(System.getProperty("headless", "true"))));
    }


    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test:" + testInfo.getDisplayName());
        //Creates new page instance
        page = browser.newPage();
        //Navigates to (Login page) in this case
        page.navigate("https://st2016.inv.bg/login");
    }

    @AfterEach
    void afterEach() {
        page.close();
    }

    @AfterAll
    static void afterAll() {
        browser.close();
    }

    @Test
    @Tag("ui")
    @DisplayName("Can login successfully with valid credentials")
    void canLoginSuccessfullyWithValidCredentials() {
        login();
    }

    @Test
    @Tag("ui")
    @DisplayName("Can login successfully with valid credentials and logout")
    void canLoginSuccessfullyWithValidCredentialsAndLogout() {
        login();
        //Logout
        page.click(userPanelSelector);
        String logoutLinkSelector = "a.selenium-button-logout";
        page.click(logoutLinkSelector);
        //Logout message check
        String logoutSuccessMsgSelector = "#okmsg";
        String logoutMsg = page.textContent(logoutSuccessMsgSelector).replace("\u00a0", ""); //TODO: report as a bug :)
        Assertions.assertEquals("Вие излязохте от акаунта си.", logoutMsg);
    }


    @ParameterizedTest
    @ValueSource(strings = {"име на артикул", "item_name", "search_test"})
    @Tag("ui")
    @Tag("positive")
    void canSearchForExistingItems(String name) {
        //Clean all existing items
        api.itemAPI().deleteAll();
        //Create new item to search for
        Item item = new Item(name, 20.00, "кг.", 10.0, "EUR");
        Response resp = api.itemAPI().createItem(item);
        Assertions.assertEquals(201, resp.statusCode());
        login();
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

    private void login() {
        String companyNameSelector = "//div[@id='wellcome']/h2";
        String companyName = page.textContent(companyNameSelector);
        Assertions.assertEquals("QA Ground", companyName);
        //Enter email
        String emailSelector = "#loginusername";
        page.fill(emailSelector, "karamfilovs@gmail.com");
        //Enter password
        String passwordSelector = "#loginpassword";
        page.fill(passwordSelector, "123456");
        //Click Login button
        page.waitForNavigation(() -> {
            page.click(loginButtonSelector);
        });
        String loggedUser = page.textContent(userPanelSelector);
        Assertions.assertEquals("karamfilovs@gmail.com", loggedUser);
    }

}
