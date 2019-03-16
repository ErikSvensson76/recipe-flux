package se.smelly.eat.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Recipe {
	
	@Id
	private String id;
	@Indexed
	private String recipeName;
	
	@Indexed
	private List<MeasuredIngredient> ingredients = new ArrayList<>();
	private List<String> instructions = new ArrayList<>();
	@Indexed
	private List<RecipeCategory> categories = new ArrayList<>();
	private String description;
	
	public Recipe(String _id, String recipeName, List<MeasuredIngredient> ingredients, List<String> instructions,
			List<RecipeCategory> categories, String description) {
		this(recipeName, ingredients, instructions, categories, description);
		this.id = _id;		
	}

	public Recipe(String recipeName, List<MeasuredIngredient> ingredients, List<String> instructions,
			List<RecipeCategory> categories, String description) {
		this(recipeName, description);		
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.categories = categories;
		
	}

	public Recipe(String recipeName, String description) {
		this.recipeName = recipeName;
		this.description = description;
	}
	
	protected Recipe() {}
	
	public boolean addInstruction(String instruction) {
		if(instruction == null) {
			throw new NullPointerException();
		}
		
		return instructions.add(instruction);
	}
	
	public boolean removeInstruction(String instruction) {
		if(instruction == null) {
			throw new NullPointerException();
		}
		
		return instructions.remove(instruction);
	}
	
	public boolean addCategory(RecipeCategory category) {
		if(category == null) {
			throw new NullPointerException();
		}
		
		return categories.contains(category) ? false : categories.add(category);
	}
	
	public boolean removeCategory(RecipeCategory category) {
		if(category == null) {
			throw new NullPointerException();
		}
		
		return categories.remove(category);
	}
	
	public boolean addIngredient(MeasuredIngredient ingredient) {
		if(ingredient == null) {
			throw new NullPointerException();
		}
		
		return ingredients.contains(ingredient) ? false : ingredients.add(ingredient);
	}
	
	public boolean removeIngredient(MeasuredIngredient ingredient) {
		if(ingredient == null) {
			throw new NullPointerException();
		}
		
		return ingredients.remove(ingredient);
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recipeName == null) ? 0 : recipeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recipeName == null) {
			if (other.recipeName != null)
				return false;
		} else if (!recipeName.equals(other.recipeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recipe [_id=" + id + ", recipeName=" + recipeName + ", description=" + description + "]";
	}	
	
}
