import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class BasicsGET {
	
	@Test
	public void testingGetRequest() {
		
		// BaseURL
		RestAssured.baseURI="https://maps.googleapis.com";
		
			given().
					param("location","-33.8670522,151.1957362").
					param("radius","500").
					param("key","AIzaSyBx2cIDOsGuk1ulcPlKjFBcq69XSTu1BZE").
			
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