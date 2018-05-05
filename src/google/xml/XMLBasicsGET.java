package google.xml;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

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

public class XMLBasicsGET {
	
	Properties prop = new Properties();
	
	@Before
	public void getData() throws IOException {
		
		FileInputStream fileloca = new FileInputStream("/Users/work/Desktop/Jawad/DemoProject/src/evn.properties");
		prop.load(fileloca);
//		prop.getProperty("HOST");  Base URL from the properties
	}
	
	
	@Test
	public void testingGetRequest() {
		
		// BaseURL
		RestAssured.baseURI = "https://maps.googleapis.com";
		
			Response response = given().log().all().
					param("location","-33.8670522,151.1957362").
					param("radius","500").
					param("key", prop.getProperty("GoogleKey")). //importing the key from env.properties
			
			when().
					get("/maps/api/place/nearbysearch/xml").
			
			then().
					assertThat().statusCode(200).and().contentType(ContentType.XML).and().
					extract().response();
			
			XmlPath xml = ReusebleMethods.rawToXML(response);
			
			System.out.println(xml.get()); // printing it all
			
			assertEquals("Sydney", xml.get("PlaceSearchResponse.result[0].name"));
			assertEquals("ChIJP3Sa8ziYEmsRUKgyFmh9AQM", xml.get("PlaceSearchResponse.result[0].place_id"));
			
			

	}

}