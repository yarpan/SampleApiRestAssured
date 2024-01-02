package simple;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class Get01_Basic {
    private static final String myBaseUrl = "https://dev.emeli.in.ua/";
    private static final String endPointGet = "/wp-json/wp/v2/posts/{postId}";
    private static final int postId = 1198;
    private static final int statusExpected = 200;


    @BeforeMethod
    public static void openBaseUrl(){
        RestAssured.baseURI = myBaseUrl;
    }

    @Test
    public void checkParamTest(){
        given()
                .when()
                .get(endPointGet,postId)
                .then()
                .statusCode(statusExpected)
                .body(not(empty()))                         // body {}
                .body("id", instanceOf(Integer.class))
                .body("id", notNullValue())              // "id" = null
                .body("id", not(empty()))                // "id" = ""
                .body("id",equalTo(postId));



    }




}













