package se.smelly.eat.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.RecipeForm;
import se.smelly.eat.models.Recipe;

public interface RecipeService {
	
	static Recipe convertFromForm(RecipeForm form) {
		return new Recipe(form.getRecipeName(), 
				form.getIngredients(), 
				form.getInstructions(), 
				form.getCategories(), 
				form.getDescription());
	}

	Mono<Recipe> save(Recipe recipe);

	Mono<Void> deleteRecipe(String id);

	Flux<Recipe> findByCategoryName(String categoryName);

	Flux<Recipe> findByRecipeName(String recipeName);

	Flux<Recipe> findByIngredientName(String ingredientName);

	Mono<Recipe> findById(String id);

	Flux<Recipe> findAll();

	Mono<Recipe> create(Mono<RecipeForm> form);

}
