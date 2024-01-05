package cucumber;

import data.TestCredentials;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ApiPage {
    private static final String BASE_URI = "https://dev.emeli.in.ua/wp-json/wp/v2/posts";
    private Response lastResponse;



    public ApiPage() {
        RestAssured.baseURI = BASE_URI;
    }

    public Response createNewPost(String title, String content) {
        lastResponse = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Basic " + TestCredentials.getCredentials())
                .body(getResponseBody(title, content))
                .post();
        return lastResponse;
    }

    public Response updatePost(String updateTitle, String updateContent, int postId) {
        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Basic " + TestCredentials.getCredentials())
                .body(getResponseBody(updateTitle, updateContent))
                .put("/" + postId);
        return response;
    }

    String getResponseBody(String title, String content) {
        return """
                 {
                    "title": "%s",
                    "content": "%s"
                }
                 """.formatted(title, content);
    }

    public Response getLastResponse() {
        return lastResponse;
    }
}
