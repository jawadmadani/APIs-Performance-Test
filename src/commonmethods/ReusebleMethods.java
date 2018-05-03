package commonmethods;


import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusebleMethods {
	
	public static XmlPath rawToXML(Response rawresponse) {
		String response = rawresponse.asString();
		
		XmlPath xmlresponse = new XmlPath(response);
		
		return xmlresponse;
	}
	
	public static JsonPath rawToJSON(Response rawresponse) {
		String response = rawresponse.asString();
		
		JsonPath jsonresponse = new JsonPath(response);
		
		return jsonresponse;
	}

}
