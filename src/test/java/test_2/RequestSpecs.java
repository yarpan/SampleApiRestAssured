package test_2;

import data.TestCredentials;
import data.TestData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class RequestSpecs {


    String getResponseBody(String title, String content) {
        return """
                 {
                    "title": "%s",
                    "content": "%s"
                }
                 """.formatted(title, content);
    }


    public RequestSpecs() {
        RestAssured.baseURI = TestData.BASE_URI;
    }

    public Response createNewPost(String title, String content) {

        return RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Basic " + TestCredentials.getCredentials())
                .body(getResponseBody(title, content))
                .post();
    }

    public Response updatePost(String updateTitle, String updateContent, int postId) {

        return RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Basic " + TestCredentials.getCredentials())
                .body(getResponseBody(updateTitle, updateContent))
                .put("/" + postId);
    }





}
