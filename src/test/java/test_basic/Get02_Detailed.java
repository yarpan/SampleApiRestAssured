package test_basic;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;


public class Get02_Detailed {
    private static final String BASE_URI = "https://dev.emeli.in.ua/";
    private static final String POSTS_ENDPOINT = "/wp-json/wp/v2/posts/{postId}";
    private static final int EXPECTED_STATUS_CODE = 200;
    private static final int postId = 11198;
    private static final String postDate = "2023-12-04T13:59:39";
    private static final Boolean sticky = true;


    @Before
    public void beforeTest() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void checkParamGet() {
        long startTime = System.currentTimeMillis();
        given()
                .when()
                .get(POSTS_ENDPOINT, postId)
                .then()
                .statusCode(EXPECTED_STATUS_CODE)
                .body(not(empty()))                         // body !{}
                .body("id", instanceOf(Integer.class))
                .body("id", notNullValue())              // "id" = null
                .body("id", not(empty()))                // "id" = ""
                .body("id", equalTo(postId))

                .body("date", instanceOf(String.class))
                .body("date", notNullValue())
                .body("date", not(empty()))
                .body("date", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}"))
                .body("date", equalTo(postDate))

                .body("guid.rendered", instanceOf(String.class))
                .body("guid.rendered", notNullValue())
                .body("guid.rendered", not(empty()))            //"status": "publish" | "unpublished"

                .body("title.gt_translate_keys", isA(List.class))
                .body("title.gt_translate_keys[0].key", equalTo("rendered"))
                .body("title.gt_translate_keys[0].format", equalTo("text"))

                .body("sticky", instanceOf(Boolean.class))
                .body("sticky", notNullValue())
                .body("sticky", not(empty()))
                .body("sticky", equalTo(sticky))

                .body("meta", isA(List.class))
                .body("meta",notNullValue())
                .body("meta", is(empty()))
                .body("meta", equalTo(Collections.emptyList()))

                .body("categories", isA(List.class))
                .body("categories", notNullValue())
                .body("categories", not(empty()))
                //.body("categories", equalTo("[1]"))
                .body("categories", hasItem(equalTo(1)))   // [1,3,5]
                .body("categories.size()", equalTo(1));     // 5 - OK
                // find Post with few categories

        long endTime = System.currentTimeMillis();
        System.out.println("responseTime: " + (endTime - startTime));
    }


}
