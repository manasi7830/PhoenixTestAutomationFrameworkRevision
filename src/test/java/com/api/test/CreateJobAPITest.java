package com.api.test;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() {
		
	
		Customer customer=new Customer("Manasi","Avachat","9767145100"," ","manasiavachat14@gmail.com","");
		CustomerAddress customerAddress=new CustomerAddress("20B","Atria","HMT","Jalahalli","Bangalore","560013","India","Karnataka");
		CustomerProduct customerProduct=new CustomerProduct("2025-04-07T18:30:00.000Z","18118034553162","18118034553162","18118034553162","2025-04-07T18:30:00.000Z",1,1);
		Problems problems=new Problems(1,"Battery Issue");
		Problems[] problemArray=new Problems[1];
		problemArray[0]=problems;
		CreateJobPayload createJobPayload=new CreateJobPayload(0,2 ,1 ,1 , customer, customerAddress, customerProduct, problemArray);
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK());
		
								
								
	}

}
