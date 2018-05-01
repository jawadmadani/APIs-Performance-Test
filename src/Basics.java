import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// BaseURL
		RestAssured.baseURI="https://maps.googleapis.com";
		
			given().
					param("location","-33.8670522,151.1957362").
					param("radious","500").
					param("Key","AIzaSyBGhqnumbaAuKbZqBBwpNDl8hboDE9KepA").
			
			when().
					get("/maps/api/place/nearbysearch/json").
			
			then().assertThat().statusCode(200);
					

	}

}