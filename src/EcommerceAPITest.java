import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

public class EcommerceAPITest {

	public static void main(String[] args) {

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("ace_kazuki@gmail.com");
		loginRequest.setUserPassword("Acekazuki@123");

		LoginResponse loginResponse = given().log().all().spec(reqSpec).body(loginRequest).when().post("/api/ecom/auth/login")
				.then().log().all().assertThat().statusCode(200).extract().response().as(LoginResponse.class);

		System.out.println("token: " + loginResponse.getToken());
		System.out.println("userId: " + loginResponse.getUserId());
		System.out.println("message: " + loginResponse.getMessage());

	}

}
