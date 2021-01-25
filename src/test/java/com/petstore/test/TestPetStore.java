/**
 * @author 
 * 
 * TestPetStore class consists of positive and Negative test-cases for Get,Add,Update and Delete pet in petstore.
 */

package com.petstore.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.petstore.prop.Config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestPetStore {

	private String id = null;

	/**
	 * Method To validate Pet's having available status
	 */

	@Test(groups = {
			"Get Pet" }, enabled = true, description = "To validate Pet's having available status", priority = 1)
	public void testGetPets() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).queryParam("status", "available").get(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_GET_PET"));
		res.then().assertThat().statusCode(200);
		res.prettyPrint();
		List<String> status = res.jsonPath().getList("status");
		for (String sta : status) {
			Assert.assertTrue(sta.equals("available"));
		}
	}

	/**
	 * Method To validate adding a pet record
	 */

	@Test(groups = { "Add PET" }, enabled = true, description = "To validate adding a pet record", priority = 2)
	public void testAddPet() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).body(Config.readJson("AddPet")).post(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		System.out.println(res.statusCode());
		res.then().assertThat().statusCode(200);
		res.prettyPrint();
		Assert.assertTrue(res.jsonPath().getString("category.name").equals("samplepet"));
		id = res.jsonPath().getString("id");
	}

	/**
	 * Method To validate pet status as sold.
	 */

	@Test(groups = { "Update Pet" }, enabled = true, description = "To validate pet status as sold", priority = 3)
	public void testUpdatePetStatus() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).body(Config.readJson("UpdatePetStatus")).put(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(200);
		res.prettyPrint();
		Assert.assertTrue(res.jsonPath().getString("status").equals("sold"));
	}

	/**
	 * Method To validate delete pet.
	 */

	@Test(groups = { "Delete Pet" }, enabled = true, description = "To validate Delete Pet API", priority = 4)
	public void testDeletePet() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).pathParam("petid", id)
				.delete(Config.loadProperties().getProperty("url")
						+ Config.loadProperties().getProperty("ENDPOINT_DELETE_PET"));
		res.then().assertThat().statusCode(200);
		res.prettyPrint();
		Assert.assertTrue(res.jsonPath().getString("message").equals(id));
	}

	/**
	 * Method To validate adding a pet record without json
	 */

	@Test(groups = {
			"Add PET" }, enabled = true, description = "To validate adding a pet record without json", priority = 5)
	public void testNegativeAddPetWithoutBody() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).post(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(405);
	}

	/**
	 * Method To validate adding a pet record with json only have id
	 */

	@Test(groups = {
			"Add PET" }, enabled = true, description = "To validate adding a pet record with json only have id", priority = 6)
	public void testNegativeAddPetWithBodyAsOnlyId() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).body("{\r\n" + "  \"id\": 456,\r\n" + " \r\n" + "}")
				.post(Config.loadProperties().getProperty("url")
						+ Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(400);
	}

	/**
	 * Method To validate adding a pet record with blank json
	 */

	@Test(groups = {
			"Add PET" }, enabled = true, description = "To validate adding a pet record with blank json", priority = 7)
	public void testNegativeAddPetBodyAsBlank() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).body("").post(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(405);
	}

	/**
	 * Method To validate updating a pet record with json only have id
	 */

	@Test(groups = {
			"Update Pet" }, enabled = true, description = "To validate updating a pet record with json only have id", priority = 8)
	public void testNegativeUpdatePetWithBodyAsOnlyId() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).body("{\r\n" + "  \"id\": 456,\r\n" + " \r\n" + "}")
				.put(Config.loadProperties().getProperty("url")
						+ Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(400);
	}

	/**
	 * Method To validate a pet record with blank json
	 */

	@Test(groups = {
			"Update Pet" }, enabled = true, description = "To validate a pet record with blank json", priority = 9)
	public void testNegativeUpdatePetWithBodyAsBlank() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).body("").put(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(405);
	}

	/**
	 * Method To validate a pet record without body
	 */

	@Test(groups = {
			"Update Pet" }, enabled = true, description = "To validate a pet record without body", priority = 10)
	public void testNegativeUpdatePetWithOutBody() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).put(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(405);
	}

	/**
	 * Method To validate Delete Pet API without id
	 */

	@Test(groups = {
			"Delete Pet" }, enabled = true, description = "To validate Delete Pet API without id", priority = 11)
	public void testNegativeDeletePetWithOutId() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).delete(
				Config.loadProperties().getProperty("url") + Config.loadProperties().getProperty("ENDPOINT_ADD_PET"));
		res.then().assertThat().statusCode(405);
	}

	/**
	 * Method To validate Delete Pet API id as Special Character
	 */

	@Test(groups = {
			"Delete Pet" }, enabled = true, description = "To validate Delete Pet API id as Special Character", priority = 12)
	public void testNegativeDeletePetIdAsSpecialCharacter() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).pathParam("petid", "@@@@@@@@")
				.delete(Config.loadProperties().getProperty("url")
						+ Config.loadProperties().getProperty("ENDPOINT_DELETE_PET"));
		res.then().assertThat().statusCode(404);
	}

	/**
	 * Method To validate Delete Pet API id as Invalid
	 */

	@Test(groups = {
			"Delete Pet" }, enabled = true, description = "To validate Delete Pet API id as Invalid", priority = 13)
	public void testNegativeDeletePetIdAsInvalid() {
		Response res;
		res = RestAssured.given().contentType(ContentType.JSON).pathParam("petid", "123456789")
				.delete(Config.loadProperties().getProperty("url")
						+ Config.loadProperties().getProperty("ENDPOINT_DELETE_PET"));
		res.then().assertThat().statusCode(404);
	}

}
