import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

public class SerializeTest {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI=  "https://rahulshettyacademy.com";
		
		AddPlace add = new AddPlace();
		add.setAccuracy(50);
		add.setAddress("29, side layout, cohen 09");
		add.setLanguage("English");
		add.setName("Frontline house");
		add.setPhone_number("(+91) 983 893 3937");
		
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(34.383494);
		add.setLocation(location);
		
		ArrayList<String> types = new ArrayList<>();
		types.add("shoe park");
		types.add("shoe");
		add.setTypes(types);
		
		add.setWebsite("http://google.com");
		
		Response res = given().log().all().queryParam("key", "qaclick123").body(add)  //java object is converted into json
		.when().post("/maps/api/place/add/json")
		.then().statusCode(200).extract().response();
		
		String response = res.asString();
		System.out.println("Reponse: "+"\n"+response);
		
		
	}

}
