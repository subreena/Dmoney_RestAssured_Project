import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class MyRestAssured {
    Properties prop = new Properties();
    public MyRestAssured() throws IOException {

        FileInputStream fs = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }
    @Test
    public void doLogin() throws ConfigurationException {
        RestAssured.baseURI = "https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json").body("{\n" +
                "    \"email\":\"admin@dmoney.com\",\n" +
                "    \"password\":\"1234\"\n" +
                "}").when().post("/user/login");
//        System.out.println(res.asString());
//        System.out.println("pass");

//        token extraction
        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);

        Utils.setEnv("token", token);
    }

    @Test
    public void searchUser() throws IOException {

        RestAssured.baseURI = "https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .when().get("/user/search/id/28603");
        System.out.println(res.asString());
    }

    @Test
    public void createUser(){
        RestAssured.baseURI = "https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .body("{\n" +
                        "    \"name\":\"TestUserInfinity123\",\n" +
                        "    \"email\":\"testInfinity@gmail.com\",\n" +
                        "    \"password\":\"1234\",\n" +
                        "    \"phone_number\": \"01234568910\",\n" +
                        "    \"nid\":\"123456789\",\n" +
                        "    \"role\":\"Agent\"\n" +
                        "}")
                .when().post("/user/create");
        System.out.println(res.asString());
    }
}
