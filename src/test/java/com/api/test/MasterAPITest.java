package com.api.test;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

import static com.api.test.SpecUtil.*;

public class MasterAPITest {
	
	@Test(description = "Verifying if the master api is giving correct response ",groups= {"api","regression","smoke"})
	public void masterAPITest() {
		given()
		.spec(requestSpecWithAuth(FD))
		.when()
		.post("master")
		.then()
		.spec(responseSpec_OK())
		.body("message",equalTo("Success"))
		.body("data",notNullValue())
		//.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("data.mst_oem.size()",equalTo(2))
		.body("data.mst_model.size()",greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name",everyItem(notNullValue()))
		.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIReponseSchema.json"));
		
		
	}
	
	@Test(description = "Verifying if the master api is giving correct status code for invalid token ",groups= {"api","negative","regression","smoke"})
	public void invalidTokenMasterAPITest() {
		given()
		.spec(requestSpec())
		.log().all()
		.when()
		.post("master")
		.then()
		.spec(responseSpec_Text(401));
		
	}

}
