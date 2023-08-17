import io.restassured.builder.RequestSpecBuilder;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

public class EcommerceAPITest {

	public static void main(String[] args) {
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginRequest login = new LoginRequest();
		login.setUserEmail("ace_kazuki@gmail.com");
		login.setUserPassword("Acekazuki@123");
		
		LoginResponse response = given().log().all().spec(req).body(login)
		.when().post("/api/ecom/auth/login")
		.then().log().all().statusCode(200).extract().response().as(LoginResponse.class);
		
		System.out.println("token: "+response.getToken());
		System.out.println("userId: "+response.getUserId());
		System.out.println("message: "+response.getMessage());

	}

}
