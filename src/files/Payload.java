package files;

public class Payload {
	
	public static String AddPlace() {
		
		return "{\r\n"
				+ "    \"location\": {\r\n"
				+ "        \"lat\": -38.383494,\r\n"
				+ "        \"lng\": 33.427362\r\n"
				+ "    },\r\n"
				+ "    \"accuracy\": 50,\r\n"
				+ "    \"name\": \"Rahul Shetty Academy\",\r\n"
				+ "    \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "    \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "    \"types\": [\r\n"
				+ "        \"shoe park\",\r\n"
				+ "        \"shop\"\r\n"
				+ "    ],\r\n"
				+ "    \"website\": \"https://rahulshettyacademy.com\",\r\n"
				+ "    \"language\": \"French-IN\"\r\n"
				+ "}";
		
	}
	
	public static String UpdatePlace() {
		
		return "{\r\n"
				+ "    \"place_id\": \"39ee59b19f9877b7a1624eb8f08cfbe1\",\r\n"
				+ "    \"address\": \"70 Summer walk, USA\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}";
		
	}
	

}
