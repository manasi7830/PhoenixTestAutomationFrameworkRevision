package com.api.test;

import static com.api.constant.Role.FD;
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
		.spec(SpecUtil.requestSpecWithAuth(FD))
		
		.when()
		.get("dashboard/count")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		
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
		.spec(SpecUtil.requestSpec())

		.when()
		.get("dashboard/count")
		.then()
		.log().all()
		.spec(SpecUtil.responseSpec_Text(401));
		
		
	}

}
