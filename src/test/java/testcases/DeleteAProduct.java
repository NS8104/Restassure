package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;

public class DeleteAProduct {
	SoftAssert softAssert = new SoftAssert();
	@Test (priority = 1)
	public void DeleteAProduct() {
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
			.body(new File("src\\main\\java\\data\\Deletebody.json")).
			//Two type of authentication basic or bearer token
			//.headers("authorization","bearer FFGGGG"). // For bearer Token
			//.auth().preemptive().basic("username", "password"). for Basic
		 when()
		    .delete("/delete.php").
		 then()
		 	.statusCode(200).
		 	 header("Content-Type", "application/json; charset=UTF-8").
		 	 extract().response();
	 	
		int actualstatuscode= reponse.getStatusCode();
		System.out.println(actualstatuscode + "  is the actualstatuscode" );
		
		String ActualHeader= reponse.getHeader("Content-Type");
		softAssert.assertEquals(ActualHeader, "application/json; charset=UTF-8");
		System.out.println(ActualHeader + ": This is actual header");
		
		String reponsebody= reponse.getBody().asString();
	    JsonPath jp = new JsonPath(reponsebody);
	    System.out.println(jp.prettify());
		String responsemessage =jp.get("message");
		System.out.println("responsemessage:" + responsemessage);
		softAssert.assertEquals(responsemessage, "Product was deleted.");
		
		softAssert.assertAll();
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
				when().get("/read_one.php").then().statusCode(404).header("Content-Type", "application/json").extract()
				.response();

		int actualstatuscode = reponse.getStatusCode();
		System.out.println(actualstatuscode + "  is the actualstatuscode");

		String ActualHeader = reponse.getHeader("Content-Type");
		Assert.assertEquals(ActualHeader, "application/json");
		System.out.println(ActualHeader + ": This is actual header");

		String reponsebody = reponse.getBody().asString();
		JsonPath jp = new JsonPath(reponsebody);
		System.out.println(jp.prettify());
		String responsemessage = jp.get("message");
		System.out.println("responsemessage:" + responsemessage);
		Assert.assertEquals(responsemessage, "Product does not exist.");
	}

}




