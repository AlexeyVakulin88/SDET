import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;


@Epic("API Testing")
@Feature("Health Check")
public class WazirXAPITest {
    private static final String API_URL = "https://api.wazirx.com/sapi/v1/tickers/24hr";

    // Set up the base URL for the API
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = API_URL;
    }

    // Verify that the API is healthy and returns a 200 status code
    @Test
    @Description("Verify API health check")
    @Severity(SeverityLevel.BLOCKER)
    public void testAPIHealth() {
        Response response = RestAssured.given().get();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "API is not healthy");
    }

    // Verify that the response content type is JSON and that the response body starts and ends with square brackets
    @Test
    @Description("Verify is json")
    @Severity(SeverityLevel.BLOCKER)
    public void testResponseJson() {
        Response response = RestAssured.get("https://api.wazirx.com/sapi/v1/tickers/24hr");
        String contentType = response.getContentType();
        Assert.assertEquals(contentType, "application/json; charset=utf-8");
        String responseString = response.getBody().asString();
        Assert.assertTrue(responseString.startsWith("[{"));
        Assert.assertTrue(responseString.endsWith("}]"));
    }

    // Verify that the API response time is within an acceptable range
    @Test
    @Description("Verify API response time is within acceptable range")
    @Severity(SeverityLevel.NORMAL)
    public void testResponseTime() {
        Response response = RestAssured.given().get();
        long responseTime = response.time();
        Assert.assertTrue(responseTime < 5000, "Response time is longer than expected");
    }

    // Verify that the API response body contains expected data (in this case, the "btcinr.ticker" field)
    @Test
    @Description("Verify API response body contains expected data")
    @Severity(SeverityLevel.NORMAL)
    public void testResponseBody() {
        Response response = RestAssured.get(API_URL);
        String responseString = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseString);
        Assert.assertTrue(jsonPath.get("btcinr.ticker") != null, "Response body does not contain expected data");
    }

    // Verify that specific fields are present in the response
    @Test
    @Description("Verify API health check")
    @Severity(SeverityLevel.BLOCKER)
    public void testResponseFields() {
        Response response = RestAssured.get("https://api.wazirx.com/sapi/v1/tickers/24hr");
        String responseString = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseString);
        Assert.assertTrue(jsonPath.getList("symbol").size() > 0);
        Assert.assertTrue(jsonPath.getList("baseAssets").size() > 0);
        Assert.assertTrue(jsonPath.getList("quoteAsset").size() > 0);
        Assert.assertTrue(jsonPath.getList("openPrice").size() > 0);
        Assert.assertTrue(jsonPath.getList("lowPrice").size() > 0);
        Assert.assertTrue(jsonPath.getList("highPrice").size() > 0);
        Assert.assertTrue(jsonPath.getList("lastPrice").size() > 0);
        Assert.assertTrue(jsonPath.getList("volume").size() > 0);
        Assert.assertTrue(jsonPath.getList("bidPrice").size() > 0);
        Assert.assertTrue(jsonPath.getList("askPrice").size() > 0);
        Assert.assertTrue(jsonPath.getList("at").size() > 0);
        Assert.assertTrue(jsonPath.getList("ticker").size() > 0);
    }
}