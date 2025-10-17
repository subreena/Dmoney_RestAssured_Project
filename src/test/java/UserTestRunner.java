import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jshell.execution.Util;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;

import java.io.IOException;
import java.util.Set;

public class UserTestRunner extends Setup {
    @Test(priority = 1, description = "admin login ")
    public void doLogin() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@dmoney.com");
        userModel.setPassword("1234");
        Response res = userController.doLogin(userModel);
        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.get("token");
        Utils.setEnv("token",token);

        System.out.println(token);
    }

    @Test(priority = 2, description = "Create user")
    public void createUser() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        Faker faker = new Faker();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress().toString());
        userModel.setPassword("1234");
        userModel.setPhone_number("0190"+ Utils.generateRandomNumber(1000000, 9999999));
        userModel.setNid("123456780");
        userModel.setRole("Customer");

        Response res = userController.createUser(userModel);
        JsonPath jsonObj = res.jsonPath();
        int userId = jsonObj.get("user.id");
        String userName = jsonObj.get("user.name");
        String userEmail = jsonObj.get("user.email");
        String userPass = jsonObj.get("user.password");
        String userPhone = jsonObj.get("user.phone_number");

        String userIdString = String.valueOf(userId);
        Utils.setEnv("id",userIdString);
        Utils.setEnv("name",userName);
        Utils.setEnv("email",userEmail);
        Utils.setEnv("password",userPass);
        Utils.setEnv("phone_number",userPhone);

        System.out.println(res.asString());
        System.out.println(userPhone);


        Assert.assertEquals(jsonObj.get("message"),"User created");
    }

    @Test(priority = 3, description = "search user")
    public void searchUser() throws IOException {
        UserController userController = new UserController(prop);
        Response res = userController.searchUser(prop.getProperty("id"));
        System.out.println(res.asString());
        JsonPath jsonObj = res.jsonPath();
        Assert.assertEquals(jsonObj.get("message"),"User found");
    }
}
