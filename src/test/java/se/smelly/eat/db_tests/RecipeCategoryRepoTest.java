package se.smelly.eat.db_tests;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import se.smelly.eat.models.RecipeCategory;
import se.smelly.eat.mongorepos.RecipeCategoryRepo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DataMongoTest
@RunWith(SpringRunner.class)
public class RecipeCategoryRepoTest {
	
	@Autowired
	private RecipeCategoryRepo testRepo;	
	
	@Before
	public void init() {
		RecipeCategory category = new RecipeCategory("Test");
		testRepo.save(category).block();
	}
	
	@After
	public void tearDown() {
		testRepo.deleteAll().block();
	}
	
	@Test
	public void test_FindByCategoryName_return_list_all_match_param() {
		String param = "Test";
		
		assertTrue(testRepo.findByCategoryNameStartingWithIgnoreCase(param)
				.all(x -> x.getCategoryName().equals(param))
				.block());				
	}

}
