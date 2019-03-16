package se.smelly.eat.service;

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

}
