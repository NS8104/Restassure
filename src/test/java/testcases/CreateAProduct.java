package testcases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateAProduct {

	@Test (priority =1)
	public void CreateAProduct() {
		/*
		 * given: all input details(base URI,Headers,Payload/Body,QueryParameters,Authentications)
		 *  when:submit api requests(Http method,Endpoint/Resource) 
		 *  then: validate response(status code, Headers, responseTime, Payload/Body)
		 * BASEuri:https://techfios.com/api-prod/api/product Headers:
		 */
		
		// Two ways to read the body to create a record body.file or body.string, For file we can create jason file and read it or as a string can be done with Hasmap
		//Hashmap takes Key and Value
		HashMap<String,String> payload = new HashMap<String,String>();
		payload.put("name", "Samsung Note 12");
		payload.put("price", "1300");
		payload.put("description" , "The best edition ever.");
	     payload.put("category_id" , "2");
	    
		Response reponse=
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.headers("Content-Type","application/json; charset=UTF-8")
			.body(payload).
		//	body(new File("src\\main\\java\\data\\Createbody.json")).
			//Two type of authentication basic or bearer token
			//.headers("authorization","bearer FFGGGG"). // For bearer Token
			//.auth().preemptive().basic("username", "password"). for Basic
			
		 when()
		    .post("/create.php").
		 then()
		 	.statusCode(201).
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
		String productmessage =jp.get("message");
		System.out.println("productmessage:" + productmessage);
		Assert.assertEquals(productmessage, "Product was created.");
		
		long Actualtime = reponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time: "+ Actualtime);
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
				.headers("Content-Type", "application/json").param("id", "2996").
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
		// Assert.assertEquals(productId, "2996");
	}

}
