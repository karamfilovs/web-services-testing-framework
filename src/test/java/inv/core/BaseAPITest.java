package inv.core;

import com.google.gson.Gson;
import inv.ClientAPITest;
import inv.api.API;
import inv.api.TokenAPI;
import inv.dto.Login;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseAPITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientAPITest.class);
    private final static TokenAPI tokenAPI = new TokenAPI("");
    private static final String EMAIL = System.getProperty("email", "karamfilovs@gmail.com");
    private static final String PASSWORD = System.getProperty("password", "111111");
    private static final String DOMAIN = System.getProperty("domain", "st2016");
    protected static API api = null;
    protected final Gson GSON = new Gson().newBuilder()
            .setPrettyPrinting()
            .create();

    @BeforeAll
    public static void beforeAllTests() {
        LOGGER.info("Setting client defaults");
        RestAssured.baseURI = System.getProperty("baseUrl", "https://api.inv.bg");
        RestAssured.basePath = System.getProperty("apiVersion", "/v3");
        LOGGER.info("Obtaining token");
        String token = tokenAPI.getToken(new Login(EMAIL, PASSWORD, DOMAIN));
        api = new API(token);
        api.clientAPI().deleteAll();
        api.itemAPI().deleteAll();
    }
}
