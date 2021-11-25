package inv.api;

import com.jayway.jsonpath.JsonPath;
import inv.dto.Client;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClientAPI extends HTTPClient {
    private static final String CLIENT_URL = "/clients";
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAPI.class);

    public ClientAPI(String token){
        super(token);
    }

    /**
     * Creates new client
     * @param client client information
     * @return response
     */
    public Response createClient(Client client){
        return post(CLIENT_URL, GSON.toJson(client));
    }

    /**
     * Returns a single client
     * @param id client id
     * @return response
     */
    public Response getClient(int id){
        return get(CLIENT_URL + "/" + id);
    }

    /**
     * Returns all clients from the system
     * @return array of clients
     */
    public Response getAll(){
        return get(CLIENT_URL);
    }

    /**
     * Deletes client
     * @param id client id
     * @return response
     */
    public Response deleteClient(int id){
        return delete(CLIENT_URL + "/" + id);
    }

    /**
     * Updates client
     * @param id client id
     * @param client client body
     * @return response
     */
    public Response updateClient(int id, Client client){
        return patch(CLIENT_URL + "/" + id, GSON.toJson(client));
    }

    public void deleteAll(){
        String responseBody = getAll().body().asString();
        List<Integer> ids = JsonPath.read(responseBody, "$.clients[*].id");
        LOGGER.debug("Ids for deletion found:" +  ids.toString());
        ids.forEach(id -> deleteClient(id));
    }
}
