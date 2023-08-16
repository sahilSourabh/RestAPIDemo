import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {

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
		
		//RequestSpecBuilder
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		//ResponseSpecBuilder
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		RequestSpecification request = given().spec(requestSpec).body(add);
		
		Response response = request.when().post("/maps/api/place/add/json")
				.then().spec(responseSpec).extract().response();
		
		/*Response response = given().log().all().spec(request).body(add)
		.when().post("/maps/api/place/add/json")
		.then().spec(responseSpec).extract().response();*/
		
		String responseString = response.asString();
		System.out.println("Reponse: "+"\n"+responseString);	

	}

}
