package com.api.test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() {
		
	
		Customer customer=new Customer("Manasi","Avachat","9767145100"," ","manasiavachat14@gmail.com","");
		CustomerAddress customerAddress=new CustomerAddress("20B","Atria","HMT","Jalahalli","Bangalore","560013","India","Karnataka");
		CustomerProduct customerProduct=new CustomerProduct("2025-04-07T18:30:00.000Z","58118034553166","58118034553166","58118034553166","2025-04-07T18:30:00.000Z",1,1);
		Problems problems=new Problems(1,"Battery Issue");
		List<Problems> problemList=new ArrayList<Problems>();
		problemList.add(problems);
		CreateJobPayload createJobPayload=new CreateJobPayload(0,2 ,1 ,1 , customer, customerAddress, customerProduct, problemList);
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",equalTo(1))
		.body("data.job_number", startsWith("JOB_"));
		
								
								
	}

}
