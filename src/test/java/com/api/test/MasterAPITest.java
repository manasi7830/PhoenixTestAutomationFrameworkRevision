package com.api.test;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() {
		given()
		.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
		.post("master")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message",equalTo("Success"))
		.body("data",notNullValue())
		//.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("data.mst_oem.size()",equalTo(2))
		.body("data.mst_model.size()",greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name",everyItem(notNullValue()))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIReponseSchema.json"));
		
		
	}
	
	@Test
	public void invalidTokenMasterAPITest() {
		given()
		.spec(SpecUtil.requestSpec())
		.log().all()
		.when()
		.post("master")
		.then()
		.spec(SpecUtil.responseSpec_Text(401));
		
	}

}
