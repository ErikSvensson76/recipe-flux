package se.smelly.eat.form;

import java.util.ArrayList;
import java.util.List;

import se.smelly.eat.models.MeasuredIngredient;
import se.smelly.eat.models.RecipeCategory;

public class RecipeForm {
	
	
	private String recipeName;
	private List<String> instructions = new ArrayList<>();
	private List<MeasuredIngredient> ingredients = new ArrayList<>();
	private List<RecipeCategory> categories = new ArrayList<>();
	private String description;
	
	
	public RecipeForm(String recipeName, List<String> instructions, List<MeasuredIngredient> ingredients,
			List<RecipeCategory> categories, String description) {
		this(recipeName, description, instructions);			
		this.ingredients = ingredients;
		this.categories = categories;
		
	}
	
	public RecipeForm(String recipeName, String description, List<String> instructions) {
		this.recipeName = recipeName;
		this.description = description;
		this.instructions = instructions;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public List<MeasuredIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<MeasuredIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<RecipeCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<RecipeCategory> categories) {
		this.categories = categories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}	
}
