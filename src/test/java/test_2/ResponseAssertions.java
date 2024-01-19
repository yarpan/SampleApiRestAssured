package test_2;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ResponseAssertions {

    public static Map<String, String> getFirstKey(Response response) {
        List<Map<String, String>> gtTranslateKeys = response.jsonPath().getList("title.gt_translate_keys");
        return gtTranslateKeys.get(0);
    }

    public static void assertId(SoftAssertions softly, Response response) {
        int actualId = parseInt(response.jsonPath().getString("id"));
        softly.assertThat(actualId).as("ID").isNotNull();
        softly.assertThat(actualId).as("ID should be an Integer").isInstanceOf(Integer.class);
    }

    public static void assertCode(SoftAssertions softly, Response response, int expectedStatus) {
        softly.assertThat(response.getStatusCode()).as("HTTP status code").isEqualTo(expectedStatus);
    }

    public static void assertKey(SoftAssertions softly, Response response) {
        softly.assertThat(getFirstKey(response).get("key")).as("gt_translate_keys key").isEqualTo("rendered");
    }

    public static void assertFormat(SoftAssertions softly, Response response) {
        softly.assertThat(getFirstKey(response).get("format")).as("gt_translate_keys format").isEqualTo("text");
    }

    public static void assertDate(SoftAssertions softly, Response response) {
        softly.assertThat((Object) response.path("date")).as("Date").isNotNull();
    }

    public static void assertStatus(SoftAssertions softly, Response response) {
        softly.assertThat((Object) response.path("status")).as("Status").isEqualTo("draft");
    }

    public static void assertContentType(SoftAssertions softly, Response response) {
        String contentType = response.getHeader("Content-Type");
        softly.assertThat(contentType).as("Content-Type").contains("application/json");
    }



}
