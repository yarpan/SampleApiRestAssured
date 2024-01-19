package test_1.test;

import data.TestData;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test_1.RequestSpecs;
import test_1.ResponseAssertions;
import report.ExtentReportListener;

import static java.lang.Integer.parseInt;


@Listeners(ExtentReportListener.class)
public class ApiAssertsTests {
    private static final String EXPECTED_TITLE = "New Post";
    private static final String UPDATED_TITLE = "Updated Put";
    private static final String EXPECTED_CONTENT = "New content for new POST";
    private static final String UPDATED_CONTENT = "Updated content by PUT";
    private RequestSpecs requestSpecs = new RequestSpecs();
    SoftAssertions softly = new SoftAssertions();
    public int postId = 11528;



    @Test
    public void createNewPostTest() {
        Response response = requestSpecs.createNewPost(EXPECTED_TITLE, EXPECTED_CONTENT);

        ResponseAssertions.assertCode(softly, response, TestData.EXPECTED_STATUS_CODE_POST);
        ResponseAssertions.assertId(softly, response);
        ResponseAssertions.assertKey(softly, response);

        ResponseAssertions.assertFormat(softly, response);
        ResponseAssertions.assertDate(softly, response);
        ResponseAssertions.assertStatus(softly, response);

        System.out.println("Create Post finished");
        softly.assertAll();
    }

    @Test
    public void updatePostTest() {
        Response response = requestSpecs.updatePost(UPDATED_TITLE, UPDATED_CONTENT, postId);
        System.out.println("postId = " + postId);
        System.out.println(response.asString());

        ResponseAssertions.assertCode(softly, response, TestData.EXPECTED_STATUS_CODE_PUT);
        ResponseAssertions.assertId(softly, response);
        ResponseAssertions.assertKey(softly, response);
        ResponseAssertions.assertFormat(softly, response);
        ResponseAssertions.assertDate(softly, response);
        ResponseAssertions.assertStatus(softly, response);

        ResponseAssertions.assertContentType(softly, response);

        System.out.println("Update Post finished");
        softly.assertAll();

    }






}
