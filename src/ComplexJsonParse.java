import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
		//Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println("Numbers of courses: "+count);
		
		//Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount: "+purchaseAmount);
		
		//Print Title of the first course
		String firstCourse = js.getString("courses[0].title");
		System.out.println("Title of first course: "+firstCourse);
		
		//Print All course titles and their respective Prices
		for(int i=0;i<count;i++) {
			
			String titles = js.getString("courses["+i+"].title");
			System.out.println("Course title: "+titles);
			
			int prices = js.getInt("courses["+i+"].price");
			System.out.println("price: "+prices);
			
		}
		
		//Print no of copies sold by RPA Course
		
		for(int i=0;i<count;i++) {
			
			String titles = js.getString("courses["+i+"].title");
			
			if(titles.equalsIgnoreCase("RPA")) {
				
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println("Numbers of copies of "+titles+": "+copies);
				break;
			}
		}
//		int copies = js.getInt("courses[2].copies");
//		System.out.println("Numbers of copies: "+copies);
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum=0;
		for(int i=0; i<count; i++) {
			
			int prices = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int total = prices*copies;
			
			sum = sum + total;
			
		}
		System.out.println("Sum of all course prices: "+sum);
		
		Assert.assertEquals(sum, purchaseAmount);
		
		
		
		
		
	}

}
