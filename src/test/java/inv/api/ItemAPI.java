package inv.api;

import com.jayway.jsonpath.JsonPath;
import inv.dto.Item;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ItemAPI extends HTTPClient {
    private static final String ITEM_URL = "/items";
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemAPI.class);

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

    public void deleteAll(){
        String responseBody = getAll().body().asString();
        List<Integer> ids = JsonPath.read(responseBody, "$.items[*].id");
        LOGGER.debug("Ids for deletion found:" +  ids.toString());
        ids.forEach(id -> deleteItem(id));
    }
}
