import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class BasicsGET {
	
	Properties prop = new Properties();
	
	@Before
	public void getData() throws IOException {
		
		FileInputStream fileloca = new FileInputStream("/Users/work/Desktop/Jawad/DemoProject/src/datafiles/evn.properties");
		prop.load(fileloca);
//		prop.getProperty("HOST");  Base URL from the properties
	}
	
	
	@Test
	public void testingGetRequest() {
		
		// BaseURL
		RestAssured.baseURI = prop.getProperty("HostURL");
		
			given().
					param("location","-33.8670522,151.1957362").
					param("radius","500").
					param("key", prop.getProperty("GoogleKey")).
			
			when().
					get("/maps/api/place/nearbysearch/json").
			
			then().
					assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("results[0].name", equalTo("Sydney")).and().
			body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
			header("Server", "scaffolding on HTTPServer2");
			
			
			// check the status code of the response
			// content type is JSON or not
			// put assertions in body to see if its returning the right values
			// header response is correct 
			

	}

}