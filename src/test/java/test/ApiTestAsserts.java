package test;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import page.ApiPage;
import page.Assertions;
import report.ExtentReportListener;


@Listeners(ExtentReportListener.class)
public class ApiTestAsserts {

    private static final String EXPECTED_TITLE = "New Post";
    private static final String EXPECTED_CONTENT = "New content for new POST";
    private ApiPage apiPage = new ApiPage();
    SoftAssertions softly = new SoftAssertions();
    public int postId = 11528;



    @Test
    public void createNewPostTest() {
        Response response = apiPage.createNewPost(EXPECTED_TITLE, EXPECTED_CONTENT);

        Assertions.assertCode(softly, response);
        Assertions.assertId(softly, response);
        Assertions.assertKey(softly, response);

        Assertions.assertFormat(softly, response);
        Assertions.assertDate(softly, response);
        Assertions.assertStatus(softly, response);

        System.out.println("Create Post finished");
        softly.assertAll();
    }


}
