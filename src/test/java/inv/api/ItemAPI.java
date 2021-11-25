package inv.api;

import inv.dto.Item;
import io.restassured.response.Response;

public class ItemAPI extends HTTPClient {
    private static final String ITEM_URL = "/items";

    public ItemAPI(String token) {
        super(token);
    }

    /**
     * Creates new item
     * @param item item information
     * @return response
     */
    public Response createItem(Item item) {
        return post(ITEM_URL, GSON.toJson(item));
    }

    /**
     * Returns a single client
     * @param id item id
     * @return response
     */
    public Response getItem(int id) {
        return get(ITEM_URL + "/" + id);
    }

    /**
     * Returns all items from the system
     *
     * @return array of items
     */
    public Response getAll() {
        return get(ITEM_URL);
    }

    /**
     * Deletes item
     *
     * @param id item id
     * @return response
     */
    public Response deleteItem(int id) {
        return delete(ITEM_URL + "/" + id);
    }

    /**
     * Updates item
     *
     * @param id   item id
     * @param item item body
     * @return response
     */
    public Response updateClient(int id, Item item) {
        return put(ITEM_URL + "/" + id, GSON.toJson(item));
    }
}
