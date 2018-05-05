package commonmethods;


import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusebleMethods {
	
	public static XmlPath rawToXML(Response rawresponse) {
		String responsexml = rawresponse.asString();
		
		XmlPath xmlresponse = new XmlPath(responsexml);
		
		return xmlresponse;
	}
	
	public static JsonPath rawToJSON(Response rawresponse) {
		String responsejson = rawresponse.asString();
		
		JsonPath jsonresponse = new JsonPath(responsejson);
		
		return jsonresponse;
	}

}
