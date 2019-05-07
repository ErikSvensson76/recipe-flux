package se.smelly.eat.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;

public class IngredientForm {
	
	@NotNull(message = "Ingredient name is required")
	@Size(min = 2, message = "Need to have at least two letters")
	private String ingredientName;
	
	@JsonCreator
	public IngredientForm(String ingredientName) {
		this.ingredientName = ingredientName;
	}	

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
}
