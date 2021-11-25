package inv;

import com.google.gson.Gson;
import inv.api.API;
import inv.api.TokenAPI;
import inv.dto.Login;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseAPITest {
    private TokenAPI tokenAPI = new TokenAPI("");
    private Login login = new Login("karamfilovs@gmail.com", "123456", "st2016");
    protected API api = new API(tokenAPI.getToken(login));
    protected final Gson GSON = new Gson().newBuilder()
            .setPrettyPrinting()
            .create();

    @BeforeAll
    public static void beforeAllTests(){
            RestAssured.baseURI = "https://api.inv.bg";
            RestAssured.basePath = "/v3";
    }
}
