import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

public class EcommerceAPITest {

	public static void main(String[] args) {

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("ace_kazuki@gmail.com");
		loginRequest.setUserPassword("Acekazuki@123");

		LoginResponse loginResponse = given().relaxedHTTPSValidation().log().all().spec(reqSpec).body(loginRequest).when().post("/api/ecom/auth/login")
				.then().log().all().assertThat().statusCode(200).extract().response().as(LoginResponse.class);

		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		System.out.println("token: " + token);
//		System.out.println("userId: " + loginResponse.getUserId());
//		System.out.println("message: " + loginResponse.getMessage());
		
		//Create Product
		
		RequestSpecification addProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		RequestSpecification addProductRequest = given().log().all().spec(addProduct)
				.param("productName", "Ace Swags").param("productAddedBy", userId)
				.param("productCategory", "fashion").param("productSubCategory", "hats").param("productPrice", "1150")
				.param("productDescription", "Addias Originals").param("productFor", "Men")
				.multiPart("productImage", new File ("C:\\Users\\Sourabh Sahil\\Postman\\files\\ace.jpg"));
		
		
		String addProductResponse = addProductRequest.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath json = new JsonPath(addProductResponse);
		String productId = json.get("productId");
		
		System.out.println("ProductID: "+ productId);
		
		//Create Order
		
		RequestSpecification createOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetail orderDetail =  new OrderDetail();
		orderDetail.setCountry("Japan");
		orderDetail.setProductOrderedId(productId);
		
		Orders order = new Orders();
		List<OrderDetail> orderList = new ArrayList<>();
		orderList.add(orderDetail);
	
		order.setOrders(orderList);
		
		String res = given().log().all().spec(createOrder).body(order).when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		JsonPath jp = new JsonPath(res);
		List<Object> poid = jp.getList("orders");
		String productOrderId = (String) poid.get(0);
		System.out.println("productOrderId: "+productOrderId);
		
		//Delete Product
		RequestSpecification delete = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		String deleteProductResponse = given().spec(delete).pathParam("productId", productId)
		.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(deleteProductResponse);
		String message = js.getString("message");
		
		Assert.assertEquals("Product Deleted Successfully", message);
		
		//Delete Order
		RequestSpecification deleteOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		String deleteOrderResponse = given().spec(deleteOrder).pathParam("productOrderId", productOrderId).when()
				.delete("/api/ecom/order/delete-order/{productOrderId}").then().log().all().extract().response()
				.asString();
		
		JsonPath jsp = new JsonPath(deleteOrderResponse);
		String msg = jsp.getString("message");
		System.out.println("Order deleted message: "+msg);
		
		Assert.assertEquals("Orders Deleted Successfully", msg);
		

	}

}
