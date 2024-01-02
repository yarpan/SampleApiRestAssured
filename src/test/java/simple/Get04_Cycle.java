package simple;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class Get04_Cycle {

    private static final String BASE_URI = "https://dev.emeli.in.ua/";
    private static final int postQuantity = 3;
    private static final String POSTS_ENDPOINT = "/wp-json/wp/v2/posts?per_page=" + postQuantity;
    private static final int EXPECTED_STATUS_CODE = 200;



    @Before
    public void beforeTest() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void checkParamGetNew() {
        long startTime = System.currentTimeMillis();
        Response response = when()
                .get(POSTS_ENDPOINT);

        response.then()
                .statusCode(EXPECTED_STATUS_CODE)
                .body(not(empty()))                         // body {}
                .body("$", hasSize(postQuantity));
                for(int i=0; i<postQuantity; i++){
                    int articleIndex = i;
                    response.then()
                            .body("[" + articleIndex + "].id", greaterThan(0))
                            .body("[" + articleIndex + "].id", instanceOf(Integer.class))
                            .body("[" + articleIndex + "].id", notNullValue())              // "id" = null
                            .body("[" + articleIndex + "].id", not(empty()));                // "id" = ""
                    System.out.println("Article " + (i+1) + " validated successfully!");
                }

        System.out.println(response.asString());

        long endTime = System.currentTimeMillis();
        System.out.println("responseTime: " + (endTime - startTime));

    }
}