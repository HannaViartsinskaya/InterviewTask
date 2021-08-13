import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Main {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test(priority = 1)
    public void testGetUserList() {

        given().get("users?page=2").then().
                statusCode(200).
                body("data.id[2]", equalTo(9)).
                log().all();

    }

    @Test(priority = 2)
    public void testGetUser() {
        given().get("users/2").then().
                statusCode(200).
                body(containsString("Janet")).
                log().all();
    }

    @Test(priority = 3)
    public void testUpdate() {
        JSONObject request = new JSONObject();
        request.put("name", "Hanna");
        request.put("job", "QA");
        given().
                body(request.toJSONString()).
                when().
                put("users/2").
                then().statusCode(200).log().body();
        System.out.println("User was updated" + request.toString());
    }

    @Test(priority = 4)
    public void addUser() {
        JSONObject request = new JSONObject();
        request.put("name", "Hanna");
        request.put("job", "QA");
        given().
                body(request.toJSONString())
                .when()
                .post("users")
                .then().statusCode(201).log().body();
        System.out.println("user was created" + request.toString());
    }

    @Test(priority = 5)
    public void deleteUser() {
        given().
                delete("users/2").
                then().
                statusCode(204).
                log().
                all();
    }
}
