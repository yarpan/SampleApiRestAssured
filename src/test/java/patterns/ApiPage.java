package patterns;

import data.TestCredentials;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ApiPage {
    private static final String BASE_URI = "https://dev.emeli.in.ua/wp-json/wp/v2/posts";


    public ApiPage() {
        RestAssured.baseURI = BASE_URI;
    }


    public Response createNewPost(String title, String content){
        String requestBody = """
             {
                "title": "%s",
                "content": "%s"
            }
             """.formatted(title, content);
        //String credentials = new String(Base64.encodeBase64((ApiData.USERNAME + ":" + ApiData.PASSWORD).getBytes()));

        return RestAssured.given()
                .header("Content-Type",ContentType.JSON)
                .header("Authorization", "Basic " + TestCredentials.getCredentials())
                .body(requestBody)
                .post();
    }

    public Response updatePost(String updateTitle, String updateContent, int postId){
        String requestBody = """
             {
                "title": "%s",
                "content": "%s"
            }
             """.formatted(updateTitle, updateContent);
        //String credentials = new String(Base64.encodeBase64((ApiData.USERNAME + ":" + ApiData.PASSWORD).getBytes()));

        return RestAssured.given()
                .header("Content-Type",ContentType.JSON)
                .header("Authorization", "Basic " + TestCredentials.getCredentials())
                .body(requestBody)
                .put("/" + postId);
    }


}
