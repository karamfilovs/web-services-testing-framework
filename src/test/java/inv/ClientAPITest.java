package inv;

import inv.core.BaseAPITest;
import inv.dto.Client;
import inv.dto.SuccessResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class ClientAPITest extends BaseAPITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientAPITest.class);


    @BeforeEach
    public void beforeEachTest(TestInfo testInfo){
        LOGGER.info("Starting test: " + testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Can get all clients")
    @Tag("api")
    void canGetAllClients() {
        Response getAllResponse = api.clientAPI().getAll();
        Assertions.assertEquals(200, getAllResponse.statusCode());
    }

    @Test
    @Tag("api")
    @DisplayName("Can create client")
    void canCreateClient() {
        Client client = new Client("Pragmatic2022" + LocalDate.now(), "Sofia", "Ivan Stranski",
                false, "Alex", "1231231212");
        Response createResponse = api.clientAPI().createClient(client);
        Assertions.assertEquals(201, createResponse.statusCode());
        Assertions.assertTrue(createResponse.getBody().asString().contains("id"));
    }

    @Test
    @Disabled("Bug in PATCH operation")
    @DisplayName("Can update existing client")
    void canUpdateExistingClient() {
        //Create client dto
        Client client = new Client("Pragmatic2025" + LocalDate.now(), "Sofia",
                "Ivan Stranski", false, "Alex", "1212112122");
        //Send create client request
        Response createResponse = api.clientAPI().createClient(client);
        Assertions.assertEquals(201, createResponse.statusCode());
        Assertions.assertTrue(createResponse.getBody().asString().contains("id"));
        //Deserialize the success response into success response object
        SuccessResponse successResponse = GSON.fromJson(createResponse.body().asString(), SuccessResponse.class);
        //Change the client name in the dto
        client.setName("Update Pragmatic Name");
        //Update the client
        Response updateResponse = api.clientAPI().updateClient(Integer.parseInt(successResponse.getId()), client);
        Assertions.assertEquals(200, updateResponse.statusCode());
    }

    @Test
    @DisplayName("Can delete existing client")
    void canDeleteExistingClient() {
        //Create client dto
        Client client = new Client("DeleteClientTest" + LocalDate.now(), "Sofia",
                "Ivan Stranski", false, "Alex", "1212112122");
        //Send create client request
        Response createResponse = api.clientAPI().createClient(client);
        Assertions.assertEquals(201, createResponse.statusCode());
        Assertions.assertTrue(createResponse.getBody().asString().contains("id"));
        //Deserialize the success response into success response object
        SuccessResponse successResponse1 = GSON.fromJson(createResponse.body().asString(), SuccessResponse.class);
        Response response = api.clientAPI().deleteClient(Integer.parseInt(successResponse1.getId()));
        Assertions.assertEquals(200, response.statusCode());
        SuccessResponse successResponse = GSON.fromJson(response.body().asString(), SuccessResponse.class);
        Assertions.assertEquals("Клиента е изтрит", successResponse.getId());
    }

    @Test
    @Disabled
    @DisplayName("Can get existing client")
    void canGetExistingClient() {
        Response response = api.clientAPI().getClient(1574);
        Assertions.assertEquals(200, response.statusCode());
    }
}
