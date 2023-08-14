import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;

public class OAuthTest {

	public static void main(String[] args) {
		
		//Get Authorisation code
		//Get access token
		//Get the results using the access token
		
		String code = "4%2F0Adeu5BVARLe3sbDloinJ1zfKPEJX4zLajjyo8_taYZPw3w5AMRztnq1-WXWqw_LMWbewhQ";
		
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
		
		//Serialization
		GetCourse results = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.as(GetCourse.class);
//		.asString();
		
		System.out.println(results.getInstructor());
		System.out.println(results.getLinkedIn());
		System.out.println(results.getCourses().getWebAutomation().get(1).getCourseTitle());
		System.out.println(results.getCourses().getWebAutomation().get(1).getPrice());
		
		
		
		
		
		
		
		
		
		

	}

}
