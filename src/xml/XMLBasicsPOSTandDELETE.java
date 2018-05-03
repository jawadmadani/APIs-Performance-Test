package xml;
import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import commonmethods.ReusebleMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XMLBasicsPOSTandDELETE {
	
	Properties prop = new Properties();
	
	@Before
	public void getData() throws IOException {
		
		FileInputStream fileloca = new FileInputStream("/Users/work/Desktop/Jawad/DemoProject/src/evn.properties");
		prop.load(fileloca);
//		prop.getProperty("HOST");  Base URL from the properties
	}
	
	@Test
	public  void testingPostAndDeleteRequests() {
		
		// Task 1, getting a return response and storing it in a string
		// Base URL
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		Response response = given().log().all().
				queryParam("key",prop.getProperty("GoogleKey")). // importing the key from evn.properties
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
				assertThat().contentType(ContentType.XML).and().
					extract().response();
		
		XmlPath xmlobj =  ReusebleMethods.rawToXML(response);
		System.out.printf(xmlobj.get("PlaceAddResponse.place_id"));
		
		// put the place_id this in delete request
		given().
				queryParam("key",prop.getProperty("GoogleKey")). // importing the key from evn.properties
				body("<PlaceDeleteRequest>" + 
						"  <place_id> " + xmlobj.get("PlaceAddResponse.place_id") + " </place_id>" + 
						"</PlaceDeleteRequest>").
		when().
				post("/maps/api/place/delete/xml").
		then().
				assertThat().contentType(ContentType.XML);
		
	}

}
