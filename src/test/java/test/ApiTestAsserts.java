package test;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pattern.RequestSpecs;
import pattern.ResponseAssertions;
import report.ExtentReportListener;


@Listeners(ExtentReportListener.class)
public class ApiTestAsserts {

    private static final String EXPECTED_TITLE = "New Post";
    private static final String EXPECTED_CONTENT = "New content for new POST";
    private RequestSpecs requestSpecs = new RequestSpecs();
    SoftAssertions softly = new SoftAssertions();
    public int postId = 11528;



    @Test
    public void createNewPostTest() {
        Response response = requestSpecs.createNewPost(EXPECTED_TITLE, EXPECTED_CONTENT);

        ResponseAssertions.assertCode(softly, response);
        ResponseAssertions.assertId(softly, response);
        ResponseAssertions.assertKey(softly, response);

        ResponseAssertions.assertFormat(softly, response);
        ResponseAssertions.assertDate(softly, response);
        ResponseAssertions.assertStatus(softly, response);

        System.out.println("Create Post finished");
        softly.assertAll();
    }


}
