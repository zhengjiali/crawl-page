package com.risk.auto.pages;

import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*; 


public class demo2 {

	@Test
	public void test(){
		useRelaxedHTTPSValidation();
		given()
		.when()
			.get("https://testerhome.com/api/v3/topics.json")
		.then()
			.body("topics.findAll{excellent=1}.excellent.sum()", greaterThan(4));
	}
}
