import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {

	public static void main(String[] args) throws IOException {
		
		//Import the request body from a JSON file
		//Convert content into Byte -> Byte data to String
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Path.of("S:\\Automation\\Automation related docs\\API Automation\\AddPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response:"+"\n"+response);
		
		JsonPath js =  new JsonPath(response);        
		String placeId = js.getString("place_id");
		System.out.println("place_id: "+placeId);

	}

}
