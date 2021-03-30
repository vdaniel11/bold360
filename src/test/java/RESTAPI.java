import java.io.IOException;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("REST API tests")
public class RESTAPI {
    @Test
    @Order(1)
    @DisplayName("Check email address")
    void name() throws IOException, JsonException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/users")
                .build();
        Response response = client.newCall(request).execute();
        Assertions.assertNotNull(response.body(), "Assert response body is not null.");
        JsonArray responseArray = (JsonArray) Jsoner.deserialize(response.body().string());

        boolean firstEmailAddress = true;
        for (Object obj : responseArray) {
            JsonObject jsonObj = (JsonObject) obj;
            String name = (String) jsonObj.get("name");
            String emailAddress = (String) jsonObj.get("email");
            if (firstEmailAddress && !emailAddress.isEmpty()) {
                firstEmailAddress = false;
                Assertions.assertTrue(emailAddress.contains("@"), "Check '@' sign in first email address");
            }
            System.out.println(name + " | " + emailAddress);
        }
    }
}
