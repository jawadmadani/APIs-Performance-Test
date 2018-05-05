package google.xml;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import commonmethods.ReusebleMethods;

public class XMLBasicsPOST {
	
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
		
		Response response = given().log().all().
				queryParam("key", prop.getProperty("GoogleKey")). //importing the key from env.properties
				body("<PlaceAddRequest>" + 
						"  <location>" + 
						"    <lat>-33.8669710</lat>" + 
						"    <lng>151.1958750</lng>" + 
						"  </location>" + 
						"  <accuracy>50</accuracy>" + 
						"  <name>Google Shoes!</name>" + 
						"  <phone_number>(02) 9374 4000</phone_number>" + 
						"  <address>48 Pirrama Road, Pyrmont, NSW 2009, Australia</address>" + 
						"  <type>shoe_store</type>" + 
						"  <website>http://www.google.com.au/</website>" + 
						"  <language>en-AU</language>" + 
						"</PlaceAddRequest>").
		when().
				post("/maps/api/place/add/xml").
		
		then().
				assertThat().statusCode(200).and().contentType(ContentType.XML).
				extract().response();
		
		XmlPath x = ReusebleMethods.rawToXML(response);
		System.out.printf(x.get("PlaceAddResponse.place_id"));
		
		// the post request adds a place in google maps 
	}

}
