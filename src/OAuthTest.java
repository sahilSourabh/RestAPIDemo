import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) {
		
		//Get Authorisation code
		//Get access token
		//Get the results using the access token
		
		String code = "4%2F0AZEOvhUwLC20I2g_5xRkOWGwlpD0IMkYsOyYTX9MUpp4XAB7egXYux6-pvUVf9pniFmyqA";
		
		//RestAssured.baseURI = "https://www.googleapis.com/oauth2/v4/token";
		
		String response = given().urlEncodingEnabled(false)
		.queryParam("code", code)
	    .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token")
//		.then().log().all().extract().response()
		.asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		String results = given().urlEncodingEnabled(false)
		.queryParam("access_token", accessToken)
		.when().log().all().get("https://rahulshettyacademy.com/getCourse.php")
		.then().log().all().extract().response()
		.asString();
		
		System.out.println(results);
		
		
		
		
		
		
		
		
		
		

	}

}
