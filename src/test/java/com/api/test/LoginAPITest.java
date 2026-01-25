package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {
	
	private UserCredentials userCredentials;
	
	@BeforeMethod(description = "Create the payload for the Login API")
	public void setup() {

		 userCredentials = new UserCredentials("iamfd", "password");
	}
	
	@Test(description = "Verifying if the login api is working for FD user ",groups= {"api","regression","smoke"})
	public void loginTest() throws IOException {

		
		        given().spec(SpecUtil.requestSpec(userCredentials))
				.when().post("login").
				then().spec(SpecUtil.responseSpec_OK())
				.and().body("message", equalTo("Success"))
				.and().body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema"));
	}

}
