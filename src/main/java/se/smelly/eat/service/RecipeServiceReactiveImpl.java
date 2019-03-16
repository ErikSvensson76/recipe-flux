package se.smelly.eat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.RecipeForm;
import se.smelly.eat.models.Recipe;
import se.smelly.eat.mongorepos.RecipeRepo;

@Service
public class RecipeServiceReactiveImpl {
	
	private RecipeRepo recipeRepo;

	@Autowired
	public RecipeServiceReactiveImpl(RecipeRepo recipeRepo) {
		this.recipeRepo = recipeRepo;
	}
	
	public Mono<Recipe> create(Mono<RecipeForm> form){
		return form.flatMap(x -> recipeRepo.save(RecipeService.convertFromForm(x)));		
	}
	
	public Flux<Recipe> findAll(){
		return recipeRepo.findAll();
	}
	
	public Mono<Recipe> findById(String id){
		return recipeRepo.findById(id);
	}
	
	public Flux<Recipe> findByIngredientName(String ingredientName){
		return recipeRepo.findRecipesByIngredientName(ingredientName);
	}
	
	public Flux<Recipe> findByRecipeName(String recipeName){
		return recipeRepo.findByRecipeName(recipeName);
	}
	
	public Flux<Recipe> findByCategoryName(String categoryName){
		return recipeRepo.findRecipesByCategoryName(categoryName);
	}
	
	public Mono<Void> deleteRecipe(String id){
		return recipeRepo.deleteById(id);
	}
	
	public Mono<Recipe> save(Recipe recipe){
		return recipeRepo.save(recipe);
	}
	
	
	
		

}
