package se.smelly.eat.mongorepos;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import se.smelly.eat.models.Recipe;

public interface RecipeRepo extends ReactiveMongoRepository<Recipe, String>{
	
	@Query("{'ingredients': {'ingredient.ingredientName': ?0}}")
	Flux<Recipe> findRecipesByIngredientName(String ingredientName);
	
	@Query("{'categories': {'categoryName': ?0}}")
	Flux<Recipe> findRecipesByCategoryName(String categoryName);
	
	Flux<Recipe> findByRecipeName(String recipeName);
	
	Flux<Recipe> findByCategoriesCategoryNameStartingWithIgnoreCase(String categoryName);
	
	Flux<Recipe> findByRecipeNameStartingWithIgnoreCase(String recipeName);
	
}
