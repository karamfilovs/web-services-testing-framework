package inv.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class HTTPClient {
    private static final String EMAIL = "karamfilovs@gmail.com";
    private static final String PASSWORD = "123456";
    protected final Gson GSON = new Gson().newBuilder()
            .setPrettyPrinting().create();

    static {
        RestAssured.baseURI = "https://st2016.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.authentication = RestAssured.preemptive().basic(EMAIL, PASSWORD);
    }

    protected Response post(String resourceUrl, String body){
        Response response = RestAssured
                .given()
                .log()
                .all()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post(resourceUrl);
        System.out.println("RESPONSE:");
        response.prettyPrint();
        return response;
    }

    protected Response put(String resourceUrl, String body){
        Response response = RestAssured
                .given()
                .log()
                .all()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .put(resourceUrl);
        System.out.println("RESPONSE:");
        response.prettyPrint();
        return response;
    }

    protected Response get(String resourceUrl){
        Response response = RestAssured
                .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .get(resourceUrl);
        System.out.println("RESPONSE:");
        response.prettyPrint();
        return response;
    }

    protected Response delete(String resourceUrl){
        Response response = RestAssured
                .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .delete(resourceUrl);
        System.out.println("RESPONSE:");
        response.prettyPrint();
        return response;
    }
}
