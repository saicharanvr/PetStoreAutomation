package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoint;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;
	public Logger logger;
	@BeforeClass
	public void setupData()
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger=LogManager.getLogger();
		
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("**********creating user ******************");
		Response response = UserEndPoint.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********user is created ******************");
	}
	
	@Test(priority=2)
	public void testUserByName()
	{
		logger.info("**********Reading user info******************");
		Response response = UserEndPoint.readUser(this.userPayload.getUsername());
		response.then().log().all();
		//response..statusCode();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********user read succeesfully******************");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("**********updating user ******************");
		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		Response response = UserEndPoint.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after update
		Response responseAfterUpdate = UserEndPoint.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		logger.info("**********updating user completed******************");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("**********deleting user ******************");
		Response response = UserEndPoint.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********deleting user completed ******************");
	}
}
