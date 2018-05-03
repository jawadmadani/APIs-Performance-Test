package json;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import commonmethods.ReusebleMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BasicsGETExtractingALL {
	
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
					get("/maps/api/place/nearbysearch/json").
			
			then().
					assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("results[0].name", equalTo("Sydney")).and().
			body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
			header("Server", "scaffolding on HTTPServer2").
			extract().response();
			
			// converting the response to json using the method in "commonmethods"
			JsonPath jsonobj =  ReusebleMethods.rawToJSON(response);
			
			int count = jsonobj.get("results.size()");
			for (int i = 0; i < count; i++) {
				System.out.printf(jsonobj.get("results["+ i +"].name"));
				System.out.println("");
			}
			System.out.println(count);
			
			// check the status code of the response
			// content type is JSON or not
			// put assertions in body to see if its returning the right values
			// header response is correct 
			

	}

}