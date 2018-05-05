package twitter.json;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import commonmethods.ReusebleMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TweetsOAuth {
	
	// credentials URL for oAuth
	// https://apps.twitter.com 
	
	String ConsumerKey="H9U7ZSd2L2CVrASaKak6mWGjW";
	String ConsumerSecret="GypoTuTaKFrY3rcEHo9JBYRpPWuaTFOThJqwrubbrmSAxqscOe";
	String Token="822553307264778240-L0bkyt6DhelFN9d6OZvZTZtxM8hgqax";
	String TokenSecret="yQHDZjEL0qTOtgxLSHqgymIeHnnbqz7Ap2cP5p4HG4hsT";
	String id;
	
	@Test
	public void getLatestTweet() {
		
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		
		Response response =	given()
				.auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret)
				.queryParam("count", "1")
		.when()
				.get("/home_timeline.json")
		.then()
				.extract().response();
	
		JsonPath jsonobj = ReusebleMethods.rawToJSON(response);
		
		System.out.println((String)jsonobj.get("text[0]"));
		System.out.println((Long)jsonobj.get("id[0]"));
	
	}
	
	
	@Test
	public void createTweet() {
		
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		
		Response response =	given()
				.auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret)
				.queryParam("status", "testing one more time #timeshere")   // tweet
		.when()
				.post("/update.json")
		.then()
				.extract().response();
		
		
		JsonPath jsonobj = ReusebleMethods.rawToJSON(response);
	
		System.out.println("Below is the tweet added");
		System.out.println((Long)jsonobj.get("id"));
		System.out.println(jsonobj.get().toString());
		
		id=jsonobj.get("id").toString();
		
	}
	
	
	@Test
	public void testingPostAndDeleteRequests() {
		
		
		// adding a tweet
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		
		Response response =	given()
				.auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret)
				.queryParam("status", "testing twitter apis using #RESTAssured")  // tweet
		.when()
				.post("/update.json")
		.then()
				.extract().response();
		
		
		JsonPath jsonobj = ReusebleMethods.rawToJSON(response);
	
		System.out.println("Below is the tweet added");
		System.out.println((Long)jsonobj.get("id"));
		System.out.println(jsonobj.get().toString());
		
		id=jsonobj.get("id").toString();
		
		// deleting the tweet
		given()
				.auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret)
		.when()
				.post("/destroy/"+id+".json")
		.then()
				.extract().response();
		
		
		// double checking we are deleting the right tweet
		JsonPath jsonobj1 = ReusebleMethods.rawToJSON(response);
		
		System.out.println("Tweet which got deleted with automation is below");
		System.out.println((String)jsonobj1.get("text"));
		System.out.println((String)jsonobj1.get("truncated"));
		
	}

}
