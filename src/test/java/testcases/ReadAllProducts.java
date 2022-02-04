package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProducts {

	@Test
	public void readAllProducts() {
		/*
		 * given: all input details(base URI,Headers,Payload/Body,QueryParameters)
		 * when: submit api requests(Http method,Endpoint/Resource) 
		 * then: validate response(status code, Headers, responseTime, Payload/Body)
		 * BASEuri:https://techfios.com/api-prod/api/product Headers:
		 */
			
		Response reponse=
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.headers("Content-Type","application/json; charset=UTF-8").
			
		 when()
		    .get("/read.php").
		 then()
		 	.statusCode(200).
		 	 header("Content-Type", "application/json; charset=UTF-8").extract().response();
	 	
		int actualstatuscode= reponse.getStatusCode();
		System.out.println(actualstatuscode + "  is the actualstatuscode" );
		
		String ActualHeader= reponse.getHeader("Content-Type");
		Assert.assertEquals(ActualHeader, "application/json; charset=UTF-8");
		System.out.println(ActualHeader + ": This is actual header");
		
		String reponsebody= reponse.getBody().asString();
//	    JsonPath jp = new JsonPath(reponsebody);
//	    System.out.println(jp.prettify());
		/*JsonPath jp = new JsonPath(responsBody);
		String productId =jp.get("id");
		System.out.println("productId:" + productId);
		Assert.assertEquals(productId, "2941");*/
	}

}
