import com.microsoft.playwright.*;
import inv.BaseAPITest;
import inv.api.API;
import inv.api.TokenAPI;
import inv.dto.Item;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LoginPageTest extends BaseAPITest {
    private static Browser browser = null;
    private Page page;
    private final String EMAIL_SELECTOR = "#loginusername";
    private final String PASSWORD_SELECTOR = "#loginpassword";
    private final String LOGIN_BTN_SELECTOR = "input.selenium-submit-button";
    private final String COMPANY_NAME_SELECTOR = "//div[@id='wellcome']/h2";
    private final String USER_PANEL_SELECTOR = "div.userpanel-header";
    private final String LOGOUT_LINK_SELECTOR = "a.selenium-button-logout";
    private final String LOGOUT_SUCCESS_MSG_SELECTOR = "#okmsg";


    @BeforeAll
    static void beforeAll() {
        browser = Playwright.create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(true));
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
    @Tag("smoke")
    @DisplayName("Can login successfully with valid credentials")
    void canLoginSuccessfullyWithValidCredentials() {
        login();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Can login successfully with valid credentials and logout")
    void canLoginSuccessfullyWithValidCredentialsAndLogout() {
        login();
        //Logout
        page.click(USER_PANEL_SELECTOR);
        page.click(LOGOUT_LINK_SELECTOR);
        //Logout message check
        String logoutMsg = page.textContent(LOGOUT_SUCCESS_MSG_SELECTOR);
        //Assertions.assertEquals("Вие излязохте от акаунта си.\n", logoutMsg);
    }


    @ParameterizedTest
    @ValueSource(strings = {"име на артикул", "item_name", "search_test"})
    @Tag("item")
    @Tag("positive")
    void canSearchForExistingItems(String name){
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
        String companyName = page.textContent(COMPANY_NAME_SELECTOR);
        Assertions.assertEquals("QA Ground", companyName);
        //Enter email
        page.fill(EMAIL_SELECTOR, "karamfilovs@gmail.com");
        //Enter password
        page.fill(PASSWORD_SELECTOR, "123456");
        //Click Login button
        page.waitForNavigation(() -> {
            page.click(LOGIN_BTN_SELECTOR);
        });
        String loggedUser = page.textContent(USER_PANEL_SELECTOR);
        Assertions.assertEquals("karamfilovs@gmail.com", loggedUser);
    }

}
