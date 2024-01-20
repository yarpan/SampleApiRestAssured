package test_basic;

import data.TestCredentials;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;

public class Post01 {
    private static final String BASE_URI = "https://dev.emeli.in.ua/wp-json/wp/v2/posts";
    private static final int EXPECTED_STATUS_CODE = 201;
    private static final String EXPECTED_TITLE = "New Post";
    private static final String EXPECTED_CONTENT = "New content for new post";
    private static final String JSON_EMPTY_ERROR = "JSON_EMPTY_ERROR";


//        String requestBody = "{\n" +
//            "  \"title\": \"" + EXPECTED_TITLE + "\",\n" +
//            "  \"content\": \"" + EXPECTED_CONTENT + "\"\n" +
//            "}";

    String requestBody = """
             {
                "title": "%s",
                "content": "%s"
            }
             """.formatted(EXPECTED_TITLE, EXPECTED_CONTENT);
    //String credentials = new String(Base64.encodeBase64((USERNAME + ":" + PASSWORD).getBytes()));

    @Before
    public void beforeTest() {
        RestAssured.baseURI = BASE_URI;
    }


    @Test
    public void createNewPostTest() {
        Response response = RestAssured
                .given()
                .header("Content-type", "application/json")
                .header("Authorization", "Basic " + TestCredentials.getCredentials())
                .body(requestBody)
                .post();


        int statusCode = response.getStatusCode();
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(statusCode).as("Response Code").isEqualTo(400);
        //softly.assertThat(statusCode).isEqualTo(EXPECTED_STATUS_CODE);
        System.out.println("statusCode = " + statusCode);
        System.out.println("EXPECTED_STATUS_CODE = " + EXPECTED_STATUS_CODE);

        checkResponseNotEmpty(response, softly);

        System.out.println("Test finished");
        softly.assertAll();

    }


    private void checkResponseNotEmpty(Response response, SoftAssertions softly) {
        //String responseBody = String.valueOf(response.body());
        String responseBody = response.getBody().asString();
        softly.assertThat(responseBody).as("Response JSON").isNotEmpty();
        System.out.println("responseBody" + responseBody);

        if (responseBody.isEmpty()) {
            System.err.println(JSON_EMPTY_ERROR);
        } else {
            parseResponse(response, softly);
        }

        System.out.println("checkResponseNotEmpty Finished");
    }


    private void parseResponse(Response response, SoftAssertions softly) {

        String actualTitleRaw = response.jsonPath().getString("title.raw");
        String actualTitleRendered = response.jsonPath().getString("title.rendered");
        List<Map<String, String>> gtTranslateKeys = response.jsonPath().getList("title.gt_translate_keys");
        String actualId = response.jsonPath().getString("id");
        Map<String, String> firstKey = gtTranslateKeys.get(0);

        softly.assertThat(firstKey.get("key")).as("gt_translate_keys key").isEqualTo("rendered");
        softly.assertThat(firstKey.get("format")).as("gt_translate_keys format").isEqualTo("text");

        softly.assertThat(actualId).as("ID").isNotNull();
        softly.assertThat(actualId).as("ID should be an Integer").isInstanceOf(Integer.class);

        softly.assertThat((Object) response.path("date")).as("Date").isNotNull();
        softly.assertThat((Object) response.path("status")).as("Status").isEqualTo("draftttt");

        // Check content-type
        String contentType = response.getHeader("Content-Type");
        softly.assertThat(contentType).as("Content-Type").contains("application/json");

        System.out.println("parseResponse Finished");
    }

}
