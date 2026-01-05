package com.api.test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CountAPITest {
	@Test
	public void verifyCountAPIResponse() {
		
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.log().uri()
		.log().method()
		.log().headers()
		.header("Authorization",AuthTokenProvider.getToken(Role.FD))
		.when()
		.get("dashboard/count")
		.then()
		.log().all()
		.statusCode(200)
		.body("message", equalTo("Success"))
		.time(lessThan(1000L))
		.body("data",notNullValue())
		.body("data.size()",equalTo(3))
		.body("data.count", everyItem(greaterThanOrEqualTo(0)))
		.body("data.label",everyItem(Matchers.not(Matchers.blankOrNullString())))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema\\CountAPIResponse-FD.json"))
		.body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"));
		
		
		
	}
	@Test
	public void countAPITest_MissingAuthToken() {
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
		
		
	}

}
