import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthTest {

	public static void main(String[] args) {
		
		//Get Authorisation code
		//Get access token
		//Get the results using the access token
		
		String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};
		
		String code = "4%2F0Adeu5BWoDwHXNP__k27cOSq1qVDDWlIKPkqASfmj62sKjs0-WZ5K-rUqdcFalOKLBO31dA";
		
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
		
		//Deserialization
		GetCourse results = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.as(GetCourse.class);
		
		System.out.println("Instructor: "+results.getInstructor());
		System.out.println("LinkedIn: "+results.getLinkedIn());
		System.out.println("CourseTitle: "+results.getCourses().getWebAutomation().get(1).getCourseTitle());
		System.out.println("CoursePrice: "+results.getCourses().getWebAutomation().get(1).getPrice());
		
		//Get price of a particular courseTitle
		List<API> courses = results.getCourses().getApi();
		
		for(int i=0;i<courses.size();i++) {
			
			if(courses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				
				System.out.println(courses.get(i).getCourseTitle()+" course Price: "+courses.get(i).getPrice());
			}
		}
		
		//Get all the coursesTitles in a particular course such as WebAutomation
		//Compare the output courseTitles with your list of titles
		List<WebAutomation> web = results.getCourses().getWebAutomation();
		
		/*for(int i=0;i<web.size();i++) {
			
			System.out.println("WebAutomation Course Titles: "+web.get(i).getCourseTitle());
		}*/
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i=0;i<web.size();i++) {
			
			list.add(web.get(i).getCourseTitle());
		}
		System.out.println("WebAutomation Course Titles: "+list);
		
		List<String> expectedList = Arrays.asList(courseTitles);
		
		Assert.assertTrue(expectedList.equals(list));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
