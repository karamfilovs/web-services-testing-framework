package inv;

import inv.core.BaseAPITest;
import inv.dto.Item;
import inv.dto.SuccessResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemAPITest extends BaseAPITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(ItemAPITest.class);

    @BeforeEach
    public void beforeEach(TestInfo testInfo){
        LOGGER.info("Starting test:" + testInfo.getDisplayName());

    }

    @Test
    @Tag("api")
    @DisplayName("Can create item with mandatory fields")
    void canCreateItem(){
        //Create new item to search for
        Item item = new Item("Automated ITEM", 20.00, "кг.", 10.0, "EUR");
        Response resp = api.itemAPI().createItem(item);
        Assertions.assertEquals(201, resp.statusCode());
    }

    @Test
    @Tag("api")
    @DisplayName("Can delete existing item")
    void canDeleteExistingItem(){
        //Create new item to search for
        Item item = new Item("Delete ITEM", 20.00, "кг.", 10.0, "EUR");
        Response resp = api.itemAPI().createItem(item);
        Assertions.assertEquals(201, resp.statusCode());
        SuccessResponse successResponse = GSON.fromJson(resp.body().asString(), SuccessResponse.class);
        //Delete the item
        Response deleteResp = api.itemAPI().deleteItem(Integer.parseInt(successResponse.getId()));
        Assertions.assertEquals(204, deleteResp.statusCode());
    }

    @Test
    @Tag("api")
    @DisplayName("Can get all items")
    void canGetAllItems(){
        Response resp = api.itemAPI().getAll();
        Assertions.assertEquals(200, resp.statusCode());
        Assertions.assertFalse(resp.body().asString().isEmpty());

    }
}
