package inv;

import inv.api.ClientAPI;
import inv.dto.Client;
import inv.dto.SuccessResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ClientAPITest extends BaseAPITest {
    private ClientAPI clientAPI = new ClientAPI();

    @Test
    @DisplayName("Can get all clients")
    void canGetAllClients(){
        Response getAllResponse = clientAPI.getAll();
        Assertions.assertEquals(200, getAllResponse.statusCode());
    }

    @Test
    @DisplayName("Can create client")
    void canCreateClient(){
        Client client = new Client("Pragmatic2022" + LocalDate.now(), "Sofia", "Ivan Stranski", false, "Alex");
        Response createResponse = clientAPI.createClient(client);
        Assertions.assertEquals(200, createResponse.statusCode());
        Assertions.assertTrue(createResponse.getBody().asString().contains("Клиента е създаден успешно!"));
    }

    @Test
    @DisplayName("Can update existing client")
    void canUpdateExistingClient(){
        //Create client dto
        Client client = new Client("Pragmatic2025" + LocalDate.now(), "Sofia", "Ivan Stranski", false, "Alex");
        //Send create client request
        Response createResponse = clientAPI.createClient(client);
        Assertions.assertEquals(200, createResponse.statusCode());
        Assertions.assertTrue(createResponse.getBody().asString().contains("Клиента е създаден успешно!"));
        //Deserialize the success response into success response object
        SuccessResponse successResponse = GSON.fromJson(createResponse.body().asString(), SuccessResponse.class);
        //Change the client name in the dto
        client.setFirm_name("Update Pragmatic Name");
        //Update the client
        Response updateResponse = clientAPI.updateClient(successResponse.getSuccess().getId(), client);
        Assertions.assertEquals(200, updateResponse.statusCode());
    }

    @Test
    @DisplayName("Can delete existing client")
    void canDeleteExistingClient(){
        Response response = clientAPI.deleteClient(1574);
        Assertions.assertEquals(200, response.statusCode());
        SuccessResponse successResponse = GSON.fromJson(response.body().asString(), SuccessResponse.class);
        Assertions.assertEquals("Клиента е изтрит", successResponse.getSuccess().getMessage());
    }

    @Test
    @DisplayName("Can get existing client")
    void canGetExistingClient(){
        Response response = clientAPI.getClient(1574);
        Assertions.assertEquals(200, response.statusCode());
    }
}
