package test_3;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class ApiTest {

    private ApiCreatePost apiPage = new ApiCreatePost();
    private static final String EXPECTED_TITLE = "New title";
    private static final String EXPECTED_CONTENT = "New content";

    private String actualTitleRaw;
    private String actualTitleRendered;
    private String actualContent;
    private Object actualId;
    private List<Map<String, String>> gtTranslateKeys;

    @Test
    public void createAndEditPostTest() {
        SoftAssertions softly = new SoftAssertions();

        // Create a new post and get the ID
        int postId = apiPage.createNewPostAndGetId(EXPECTED_TITLE, EXPECTED_CONTENT);

        // Log the request and response details for creation
        System.out.println("Created Post ID: " + postId);

        // Update the existing post and get the response
        Response updateResponse = apiPage.updatePostAndGetResponse(postId, "Updated title", "Updated content");

        // Log the request and response details for the update
        System.out.println("Update Response: " + updateResponse.getBody().asString());

        // Fetch the updated post data
        Response getUpdatedPostResponse = apiPage.getPost(postId);

        // Log the request and response details for fetching the updated post
        System.out.println("Get Updated Post Response: " + getUpdatedPostResponse.getBody().asString());

        // Check the response status for fetching the updated post
        softly.assertThat(getUpdatedPostResponse.getStatusCode()).as("Get Updated Post Response Code").isEqualTo(200);

        // Extract data from the response
        String updatedTitleRendered = getUpdatedPostResponse.jsonPath().getString("title.rendered");
        String updatedContentRendered = getUpdatedPostResponse.jsonPath().getString("content.rendered");

        // Check if the data has been successfully updated
        softly.assertThat(updatedTitleRendered).as("Updated Title rendered in the response").isEqualTo("Updated title");
        softly.assertThat(updatedContentRendered.trim()).as("Updated Content in the response").isEqualTo("<p>Updated content</p>");

        softly.assertAll();
    }
}

