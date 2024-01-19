package test_2;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test_1.RequestSpecs;
import report.ExtentReportListener;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Listeners (ExtentReportListener.class)
public class ApiTests {
    private static final int EXPECTED_STATUS_CODE_POST = 201;
    private static final int EXPECTED_STATUS_CODE_PUT = 200;
    private static final String EXPECTED_TITLE = "New Post";
    private static final String UPDATED_TITLE = "Updated Put";
    private static final String EXPECTED_CONTENT = "New content for new POST";
    private static final String UPDATED_CONTENT = "Updated content by PUT";
    private RequestSpecs requestSpecs = new RequestSpecs();
    SoftAssertions softly = new SoftAssertions();
    public int postId;// = 11528;


    @BeforeClass
    public void beforeAll(){

    }

    @Test
    public void createNewPostTest() {
        Response response = requestSpecs.createNewPost(EXPECTED_TITLE, EXPECTED_CONTENT);
        List<Map<String, String>> gtTranslateKeys = response.jsonPath().getList("title.gt_translate_keys");
        int actualId = parseInt(response.jsonPath().getString("id"));
        Map<String, String> firstKey = gtTranslateKeys.get(0);

        postId = actualId;

        softly.assertThat(response.getStatusCode()).as("status code").isEqualTo(EXPECTED_STATUS_CODE_POST);
        softly.assertThat(firstKey.get("key")).as("gt_translate_keys key").isEqualTo("rendered");
        softly.assertThat(firstKey.get("format")).as("gt_translate_keys format").isEqualTo("text");
        softly.assertThat(actualId).as("ID").isNotNull();
        softly.assertThat(actualId).as("ID should be an Integer").isInstanceOf(Integer.class);
        softly.assertThat((Object) response.path("date")).as("Date").isNotNull();
        softly.assertThat((Object) response.path("status")).as("Status").isEqualTo("draft");

        // Check content-type
        String contentType = response.getHeader("Content-Type");
        softly.assertThat(contentType).as("Content-Type").contains("application/json");

        System.out.println("Create Post finished");
        softly.assertAll();

    }

    @Test
    public void updatePostTest() {
        Response response = requestSpecs.updatePost(UPDATED_TITLE, UPDATED_CONTENT, postId);
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
        softly.assertThat((Object) response.path("status")).as("Status").isEqualTo("draft");

        // Check content-type
        String contentType = response.getHeader("Content-Type");
        softly.assertThat(contentType).as("Content-Type").contains("application/json");

        System.out.println("Update Post finished");
        softly.assertAll();

    }















}
