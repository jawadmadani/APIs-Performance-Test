package xml;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BasicsPOSTandDELETE {
	
	Properties prop = new Properties();
	
	@Before
	public void getData() throws IOException {
		
		FileInputStream fileloca = new FileInputStream("/Users/work/Desktop/Jawad/DemoProject/src/evn.properties");
		prop.load(fileloca);
//		prop.getProperty("HOST");  Base URL from the properties
	}
	
	@Test
	public  void testingPostAndDeleteRequests() {
		
		String id = "{"+
				  "\"location\": {"+
				    "\"lat\": -33.8669710,"+
				    "\"lng\": 151.1958750"+
				  "},"+
				  "\"accuracy\": 50,"+
				  "\"name\": \"Google Shoes!\","+
				  "\"phone_number\": \"(02) 9374 4000\","+
				  "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+
				  "\"types\": [\"shoe_store\"],"+
				  "\"website\": \"http://www.google.com.au/\","+
				  "\"language\": \"en-AU\""+
				"}";
		
		
		// Task 1, getting a return response and storing it in a string
		// Base URL
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		Response response = given().
				queryParam("key",prop.getProperty("GoogleKey")). // importing the key from evn.properties
				body(id).
		when().
				post("/maps/api/place/add/json").
		
		then().
				assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
					body("status", equalTo("OK")).and().extract().response();
		
		
		// Task 2, extracting the place_id from the response
		String responseString = response.asString();		// converting it to string

		JsonPath js = new JsonPath(responseString);			// converting to JSON	
		
		String placeid = js.get("place_id");				// grabbing the place_id from it as it is in JSON now
		System.out.println(placeid);
		
		
		// put the place_id this in delete request
		given().
				queryParam("key",prop.getProperty("GoogleKey")). // importing the key from evn.properties
				body("{" + 
						"  \"place_id\": \"" + placeid + "\"" + 
						"}").
		when().
				post("/maps/api/place/delete/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("status", equalTo("OK"));
	}

}
