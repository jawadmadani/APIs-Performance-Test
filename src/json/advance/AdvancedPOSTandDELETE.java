package json.advance;

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
import json.advance.datafiles.PayLoad;
import json.advance.datafiles.Resources;

public class AdvancedPOSTandDELETE {
	
	Properties prop = new Properties();
	
	@Before
	public void getData() throws IOException {
		
		FileInputStream fileloca = new FileInputStream("/Users/work/Desktop/Jawad/DemoProject/src/evn.properties");  // in windows it's //Users//work//..
		prop.load(fileloca);
//		prop.getProperty("HOST");  Base URL from the properties
	}
	
	@Test
	public  void testingPostAndDeleteRequests() {
		
		// Task 1, getting a return response and storing it in a string
		// Base URL
		RestAssured.baseURI = prop.getProperty("HostURL");
		
		Response response = given().
				queryParam("key",prop.getProperty("GoogleKey")).
				body(PayLoad.getPostData()).   // getting the post data from PayLoad class 
		when().
				post(Resources.placePostData()).
		
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
				queryParam("key",prop.getProperty("GoogleKey")).
				body("{" + 
						"  \"place_id\": \"" + placeid + "\"" + 
						"}").
		when().
				post(Resources.placeDeleteData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("status", equalTo("OK"));
	}

}
