package inv.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HTTPClient {
    private String token = null;
    protected final Gson GSON = new Gson()
            .newBuilder()
            .setPrettyPrinting()
            .create();

    public HTTPClient(String token) {
        this.token = token;
    }


    protected Response post(String resourceUrl, String body) {
        return baseRequest()
                .body(body)
                .when()
                .post(resourceUrl).prettyPeek();
    }

    protected Response put(String resourceUrl, String body) {
        return baseRequest()
                .body(body)
                .when()
                .put(resourceUrl)
                .prettyPeek();
    }

    protected Response patch(String resourceUrl, String body) {
        return baseRequest()
                .body(body)
                .when()
                .patch(resourceUrl)
                .prettyPeek();
    }

    protected Response get(String resourceUrl) {
        return baseRequest()
                .when()
                .get(resourceUrl)
                .prettyPeek();
    }

    protected Response delete(String resourceUrl) {
        return baseRequest()
                .when()
                .delete(resourceUrl)
                .prettyPeek();
    }

    private RequestSpecification baseRequest() {
        return RestAssured.given()
                .auth()
                .oauth2(token)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
