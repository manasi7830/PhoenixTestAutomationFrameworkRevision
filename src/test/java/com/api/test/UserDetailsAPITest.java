package com.api.test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.AuthTokenProvider;

import io.restassured.http.Header;

import static com.api.test.SpecUtil.*;


public class UserDetailsAPITest {
	
	@Test(description = "Verify if the Userdetails API response is shown correctly" ,groups= {"api","smoke","regression"})
	public void UserDetails() throws IOException {
		
		
		Header authHeader= new Header("Authorization",AuthTokenProvider.getToken(FD));
		given()
		.spec(requestSpecWithAuth(FD))
		.when()
		.get("userdetails")
		.then()
		.spec(responseSpec_OK())
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
		
		
		
	}

}
