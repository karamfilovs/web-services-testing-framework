package inv.api;

import inv.dto.Client;
import io.restassured.response.Response;

public class ClientAPI extends HTTPClient {
    private static final String CLIENT_URL = "/client";
    private static final String CLIENTS_URL = "/clients";

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
        return get(CLIENTS_URL);
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
        return put(CLIENT_URL + "/" + id, GSON.toJson(client));
    }
}
