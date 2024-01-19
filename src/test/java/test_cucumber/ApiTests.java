package test_cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Listeners;
import report.ExtentReportListener;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Listeners(ExtentReportListener.class)
public class ApiTests {
    private static final int EXPECTED_STATUS_CODE_POST = 201;
    private static final int EXPECTED_STATUS_CODE_PUT = 200;
    private static final String EXPECTED_TITLE = "New title";
    private static final String UPDATED_TITLE = "Updated Put";
    private static final String EXPECTED_CONTENT = "New content for new POST";
    private static final String UPDATED_CONTENT = "Updated content by PUT";
    private ApiPage apiPage = new ApiPage();
    SoftAssertions softly = new SoftAssertions();
    public int postId = 11528;


    @Given("the API is accessible")
    public void theApiIsAccessible() {
        System.out.println("test");
    }

    @When("a new post is created with title {string} and content {string}")
    public void newPostCreated(String title, String content) {
        apiPage.createNewPost(title, content);
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int statusCode) {
        Response response = apiPage.getLastResponse();
        softly.assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }

    @And("the response should contain the expected title and content")
            public void responseContains() {
        Response response = apiPage.getLastResponse();

        List<Map<String, String>> gtTranslateKeys = response.jsonPath().getList("title.gt_translate_keys");
        int actualId = parseInt(response.jsonPath().getString("id"));
        Map<String, String> firstKey = gtTranslateKeys.get(0);

        softly.assertThat(EXPECTED_TITLE)
                .as("Title raw in the response")
                .isEqualTo(response.jsonPath().getString("title.raw"));


        softly.assertThat(firstKey.get("key")).as("gt_translate_keys key").isEqualTo("rendered");
        softly.assertThat(firstKey.get("format")).as("gt_translate_keys format").isEqualTo("text");
        softly.assertThat(actualId).as("ID").isNotNull();
        softly.assertThat(actualId).as("ID should be an Integer").isInstanceOf(Integer.class);
        softly.assertThat((Object) response.path("date")).as("Date").isNotNull();
        softly.assertThat((Object) response.path("status")).as("Status").isEqualTo("draft");
        String contentType = response.getHeader("Content-Type");
        softly.assertThat(contentType).as("Content-Type").contains("application/json");
        System.out.println("Create Post finished");
        softly.assertAll();
    }


    //@Test
    public void updatePostTest() {
        Response response = apiPage.updatePost(UPDATED_TITLE, UPDATED_CONTENT, postId);
        System.out.println("postId = " + postId);
        System.out.println(response.asString());
        List<Map<String, String>> gtTranslateKeys = response.jsonPath().getList("title.gt_translate_keys");
        String actualIds = response.jsonPath().getString("id");
        System.out.println("actualIds = " + actualIds);

        int actualId = parseInt(response.jsonPath().getString("id"));
        Map<String, String> firstKey = gtTranslateKeys.get(0);

        softly.assertThat(response.getStatusCode()).as("status code").isEqualTo(EXPECTED_STATUS_CODE_PUT);
        softly.assertThat(firstKey.get("key")).as("gt_translate_keys key").isEqualTo("rendered");
        softly.assertThat(firstKey.get("format")).as("gt_translate_keys format").isEqualTo("text");
        softly.assertThat(actualId).as("ID").isNotNull();
        softly.assertThat(actualId).as("ID should be an Integer").isInstanceOf(Integer.class);
        softly.assertThat((Object) response.path("date")).as("Date").isNotNull();
        softly.assertThat((Object) response.path("status")).as("Status").isEqualTo("drafttttt");

        // Check content-type
        String contentType = response.getHeader("Content-Type");
        softly.assertThat(contentType).as("Content-Type").contains("application/json");

        System.out.println("Update Post finished");
        softly.assertAll();

    }


}
