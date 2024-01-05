package page;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.constant.ConstantDescs.NULL;
import static org.assertj.core.api.Assertions.assertThat;

public class PostPage {

    private static final int EXPECTED_STATUS_CODE_POST = 2001;
    private static final int EXPECTED_STATUS_CODE_PUT = 200;
    private static final String EXPECTED_TITLE = "New Post";
    private static final String UPDATED_TITLE = "Updated Put";
    private static final String EXPECTED_CONTENT = "New content for new POST";
    private static final String UPDATED_CONTENT = "Updated content by PUT";
    private ApiPage apiPage = new ApiPage();
    SoftAssertions softly = new SoftAssertions();
    public int postId = 11528;


    public boolean checkStatusCode(Response response) {
        int statusActual = response.getStatusCode();
        return statusActual == EXPECTED_STATUS_CODE_POST;
    }

//    public int getId(Response response) {
//        int actualId = parseInt(response.jsonPath().getString("id"));
//        return !actualId.equals(NULL);
//    }

    public boolean checkId(Response response) {
        Integer actualId = parseInt(response.jsonPath().getString("id"));
        return true;
    }
}
