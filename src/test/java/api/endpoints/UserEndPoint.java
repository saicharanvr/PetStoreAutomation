package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//UserEndPoint.java
//Created to perform Create, Read, Update, Delete Requests to the user API

public class UserEndPoint {
	
	public static Response createUser(User payload)
	{
		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(payload)
			.when()
				.post(routes.post_url);
				
			return response;
	}
	
	public static Response readUser(String userName)
	{
		Response response = given()
				.pathParam("username", userName)
			.when()
				.get(routes.get_url); //Here no need to attach pathparam explicitly as it goes with the request
				
			return response;
	}
	
	public static Response updateUser(String userName, User payload)
	{
		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username", userName)
				.body(payload)
			.when()
				.put(routes.update_url);
				
			return response;
	}

	public static Response deleteUser(String userName)
	{
		Response response = given()
				.pathParam("username", userName)
			.when()
				.delete(routes.delete_url); //Here no need to attach pathparam explicitly as it goes with the request
				
			return response;
	}
	


}
