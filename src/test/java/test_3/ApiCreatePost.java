package test_3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;

public class ApiCreatePost {

    private static final String BASE_URI = "https://dev.emeli.in.ua/wp-json/wp/v2/posts";
    private static String USERNAME = "admin";
    private static String PASSWORD = "Engineer_123";

    public ApiCreatePost() {
        initialize();
    }

    public ApiCreatePost(String baseUri, String username, String password) {
        RestAssured.baseURI = baseUri;
        USERNAME = username;
        PASSWORD = password;
        initialize();
    }

    private void initialize() {
        RestAssured.baseURI = BASE_URI;
    }

    public int createNewPostAndGetId(String title, String content) {
        String requestBody = "{\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"content\": \"" + content + "\"\n" +
                "}";

        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Basic " + getEncodedCredentials())
                .body(requestBody)
                .post();

        return response.jsonPath().getInt("id");
    }

    public Response updatePostAndGetResponse(int postId, String updatedTitle, String updatedContent) {
        String requestBody = "{\n" +
                "  \"title\": \"" + updatedTitle + "\",\n" +
                "  \"content\": \"" + updatedContent + "\"\n" +
                "}";

        return RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Basic " + getEncodedCredentials())
                .body(requestBody)
                .put("/" + postId);
    }
    public Response getPost(int postId) {
        return RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Basic " + getEncodedCredentials())
                .get("/" + postId);
    }

    private String getEncodedCredentials() {
        return new String(Base64.encodeBase64((USERNAME + ":" + PASSWORD).getBytes()));
    }


}
