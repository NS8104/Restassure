package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;

public class UpdateAProduct {

	@Test (priority = 1)
	public void UpdateAProduct() {
		/*
		 * given: all input details(base URI,Headers,Payload/Body,QueryParameters,Authentications)
		 *  when:submit api requests(Http method,Endpoint/Resource) 
		 *  then: validate response(status code, Headers, responseTime, Payload/Body)
		 * BASEuri:https://techfios.com/api-prod/api/product Headers:
		 */
		Response reponse=
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.headers("Content-Type","application/json; charset=UTF-8")
			.body(new File("src\\main\\java\\data\\Updatebody.json")).
			//Two type of authentication basic or bearer token
			//.headers("authorization","bearer FFGGGG"). // For bearer Token
			//.auth().preemptive().basic("username", "password"). for Basic
		 when()
		    .put("/update.php").
		 then()
		 	.statusCode(200).
		 	 header("Content-Type", "application/json; charset=UTF-8").
		 	 extract().response();
	 	
		int actualstatuscode= reponse.getStatusCode();
		System.out.println(actualstatuscode + "  is the actualstatuscode" );
		
		String ActualHeader= reponse.getHeader("Content-Type");
		Assert.assertEquals(ActualHeader, "application/json; charset=UTF-8");
		System.out.println(ActualHeader + ": This is actual header");
		
		String reponsebody= reponse.getBody().asString();
	    JsonPath jp = new JsonPath(reponsebody);
	    System.out.println(jp.prettify());
		String responsemessage =jp.get("message");
		System.out.println("responsemessage:" + responsemessage);
		Assert.assertEquals(responsemessage, "Product was updated.");
	}
	@Test(priority = 2)
	public void readAProduct() {
		/*
		 * given: all input details(base
		 * URI,Headers,Payload/Body,QueryParameters,Authentications) when:submit api
		 * requests(Http method,Endpoint/Resource) then: validate response(status code,
		 * Headers, responseTime, Payload/Body)
		 * BASEuri:https://techfios.com/api-prod/api/product Headers:
		 */
		Response reponse = given().baseUri("https://techfios.com/api-prod/api/product")
				.headers("Content-Type", "application/json").param("id", "2997").
				// Two type of authentication basic or bearer token
				// .headers("authorization","bearer FFGGGG"). // For bearer Token
				// .auth().preemptive().basic("username", "password"). for Basic
				when().get("/read_one.php").then().statusCode(200).header("Content-Type", "application/json").extract()
				.response();

		int actualstatuscode = reponse.getStatusCode();
		System.out.println(actualstatuscode + "  is the actualstatuscode");

		String ActualHeader = reponse.getHeader("Content-Type");
		Assert.assertEquals(ActualHeader, "application/json");
		System.out.println(ActualHeader + ": This is actual header");

		String reponsebody = reponse.getBody().asString();
		JsonPath jp = new JsonPath(reponsebody);
		System.out.println(jp.prettify());
		String productId = jp.get("id");
		System.out.println("productId:" + productId);
		// Assert.assertEquals(productId, "2997");
	}

}
