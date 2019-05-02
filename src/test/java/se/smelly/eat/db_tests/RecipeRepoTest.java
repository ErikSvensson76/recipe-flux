package se.smelly.eat.db_tests;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.models.MeasuredIngredient;
import se.smelly.eat.models.Measurement;
import se.smelly.eat.models.Recipe;
import se.smelly.eat.models.RecipeCategory;
import se.smelly.eat.mongorepos.IngredientRepo;
import se.smelly.eat.mongorepos.RecipeCategoryRepo;
import se.smelly.eat.mongorepos.RecipeRepo;



@DataMongoTest
@RunWith(SpringRunner.class)
public class RecipeRepoTest {
	
	
	@Autowired private RecipeRepo testRepo;
	@Autowired private RecipeCategoryRepo categoryRepo;
	@Autowired private IngredientRepo ingredientRepo;
	
	
	private static final String INGREDIENT_NAME = "Spam";
	private static final String CATEGORY_NAME = "Test";
	private static final String RECIPE_NAME = "Spam stew";
	
	
	@Before
	public void init() {
		RecipeCategory cat = categoryRepo.save(new RecipeCategory(CATEGORY_NAME)).block();
		Ingredient ing = ingredientRepo.save(new Ingredient(INGREDIENT_NAME)).block();
		MeasuredIngredient temp = new MeasuredIngredient(ing, Measurement.KG, 1.5);
		Recipe tempRecipe = new Recipe(RECIPE_NAME, "Test description");
		tempRecipe.addCategory(cat);
		tempRecipe.addIngredient(temp);
		testRepo.save(tempRecipe).block();		
	}
	
	@After
	public void tearDown() {
		ingredientRepo.deleteAll().block();
		categoryRepo.deleteAll().block();
		testRepo.deleteAll().block();
	}
	
	@Test
	public void test_findByRecipeName_return_flux_all_containing_RECIPE_NAME() {
		assertTrue(testRepo.findByRecipeName(RECIPE_NAME)
				.all(r -> r.getRecipeName().equals(RECIPE_NAME))
				.block());
	}
	
	@Test
	public void test_findRecipesByIngredientName_all_containing_INGREDIENT_NAME() {		
		Flux<Recipe> source = testRepo.findRecipesByIngredientName(INGREDIENT_NAME);
		List<MeasuredIngredient> ingredients = source.flatMapIterable(x-> x.getIngredients()).collect(Collectors.toList()).block();		
		assertTrue(ingredients.stream()
				.allMatch(x -> x.getIngredient()
						.getIngredientName()
						.equals(INGREDIENT_NAME))
				);				
	}
	
	@Test
	public void test_findRecipesByCategoryName_all_RecipeCategories_containing_CATEGORY_NAME() {
		assertTrue(testRepo.findRecipesByCategoryName(CATEGORY_NAME)
				.flatMapIterable(recipe -> recipe.getCategories())
				.all(x -> x.getCategoryName().equals(CATEGORY_NAME))
				.block());
	}
	
	@Test
	public void test_findByCategoriesCategoryNameStartingWithIgnoreCase() {
		String searchWord = "te";
		String expectedContent = "Te";
		
		assertTrue(testRepo.findByCategoriesCategoryNameStartingWithIgnoreCase(searchWord)
				.flatMapIterable(Recipe::getCategories)
				.map(RecipeCategory::getCategoryName)
				.all(name -> name.startsWith(expectedContent))
				.block());		
	}
	
	@Test
	public void test_findByRecipeNameStartingWithIgnoreCase() {
		String searchWord = "SpA";
		String expectedContent = "Spa";
		long expectedNumberOfRecipes = 1;
		
		Flux<Recipe> result = testRepo.findByRecipeNameStartingWithIgnoreCase(searchWord);
				
		assertTrue(expectedNumberOfRecipes == result.count().block());
		assertTrue(result
				.map(Recipe::getRecipeName)
				.all(name -> name.startsWith(expectedContent))
				.block());		
	}
	

}
