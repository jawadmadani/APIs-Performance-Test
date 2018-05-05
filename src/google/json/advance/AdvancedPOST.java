package google.json.advance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import google.json.advance.datafiles.PayLoad;
import google.json.advance.datafiles.Resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AdvancedPOST {
	
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
		RestAssured.baseURI = prop.getProperty("HostURL");  // importing from an external file
		
		given().log().all().
				queryParam("key", prop.getProperty("GoogleKey")).  // importing from an external file
				body(PayLoad.getPostData()).  // getting the post data from payload
		when().
				post(Resources.placePostData()). // importing from an external class
		
		then().
				assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				
		body("status", equalTo("OK"));
		
		// the post request adds a place in google maps 
	}

}
