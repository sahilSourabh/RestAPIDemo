import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="getData")
	public void addBook(String isbn, String aisle) {
		
		//Dynamically build JsonPayload with external data inputs
		//Parametrize API tests with multiple data inputs
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String addBookResponse = given().header("Content-Type", "application/json")
		.body(Payload.AddBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		
		JsonPath js = new JsonPath(addBookResponse);
		String id = js.get("ID");
		System.out.println("Book ID: "+id);
		
	}
	
	
	
	@DataProvider
	public Object[][] getData() {
		
		return new Object [][] { {"bcd","2270"},{"bcd","2271"},{"bcd","2277"},{"bcd","2276"}};
	}

}
