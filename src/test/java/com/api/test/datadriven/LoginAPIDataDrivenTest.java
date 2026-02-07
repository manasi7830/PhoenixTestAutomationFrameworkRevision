package com.api.test.datadriven;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;

import static com.api.test.SpecUtil.*;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPIDataDrivenTest {
	
	
	
	
	
	@Test(description = "Verifying if the login api is working for FD user ",groups= {"api","regression","datadriven"},
			dataProviderClass = com.dataproviders.DataProvidersUtils.class,
			dataProvider = "LoginAPIDataProvider"
	
			)
	public void loginTest(UserBean userbean)  {

		
		        given().spec(requestSpec(userbean))
				.when().post("login").
				then().spec(responseSpec_OK())
				.and().body("message", equalTo("Success"))
				.and().body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema"));
	}

}
