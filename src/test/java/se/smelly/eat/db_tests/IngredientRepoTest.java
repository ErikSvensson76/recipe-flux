package se.smelly.eat.db_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.test.StepVerifier;

import static org.junit.Assert.*;

import se.smelly.eat.models.Ingredient;
import se.smelly.eat.mongorepos.IngredientRepo;

@DataMongoTest
@RunWith(SpringRunner.class)
public class IngredientRepoTest {
	
	@Autowired
	private IngredientRepo testRepo;
	
	private Ingredient testIngredient;
	
	
	
	@Before
	public void init() {
		testIngredient = new Ingredient("Spam");
		testIngredient = testRepo.save(testIngredient).log().block();
		testIngredient.getId();
	}
	
	@After
	public void tearDown() {
		testRepo.deleteAll().block();
	}
	
	@Test
	public void testFindTestIngredientByName() {
		Ingredient expected = testIngredient;
		String ingredientName = "Spam";
		
		Ingredient result = testRepo.findFirstByIngredientName(ingredientName).block();
		
		assertEquals(expected, result);
	}
	
	@Test
	public void findByIngredientNameStartingWithIgnoreCase() {
		String param = "sP";
		
		StepVerifier
			.create(testRepo.findByIngredientNameStartingWithIgnoreCase(param))
			.expectNextMatches(x -> x.getIngredientName().toLowerCase().startsWith(param.toLowerCase()))			
			.verifyComplete();
	}
	
		
	

}
