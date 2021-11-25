package inv.api;

import inv.dto.Login;
import inv.dto.TokenResponse;

public class TokenAPI extends HTTPClient {
    private static final String TOKEN_URL = "/login/token";

    public TokenAPI(String token) {
        super(token);
    }

    public String getToken(Login login) {
        String responseString = post(TOKEN_URL, GSON.toJson(login)).getBody()
                .asString();
        //return JsonPath.from(responseString).get("$.token").toString();
        return GSON.fromJson(responseString, TokenResponse.class)
                .getToken();
    }
}
