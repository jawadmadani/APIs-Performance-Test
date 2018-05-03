package json;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class BasicsPOST {
	
	Properties prop = new Properties();
	@Before
	public void getData() throws IOException {
		
		FileInputStream fileloca = new FileInputStream("/Users/work/Desktop/Jawad/DemoProject/src/evn.properties");
		prop.load(fileloca);
//		prop.getProperty("HOST");  Base URL from the properties
	}
	
	
	@Test
	public void testingPostRequest() {
		
		// BaseURL
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		given().log().all().
				queryParam("key", prop.getProperty("GoogleKey")). //importing the key from env.properties
				body("{"+
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
						"}").
		when().
				post("/maps/api/place/add/json").
		
		then().
				assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				
		body("status", equalTo("OK"));
		
		// the post request adds a place in google maps 
	}

}
