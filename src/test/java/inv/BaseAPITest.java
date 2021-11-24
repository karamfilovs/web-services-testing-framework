package inv;

import com.google.gson.Gson;

public class BaseAPITest {
    protected final Gson GSON = new Gson().newBuilder()
            .setPrettyPrinting()
            .create();
}
