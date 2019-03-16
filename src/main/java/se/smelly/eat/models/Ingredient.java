package se.smelly.eat.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Ingredient {
	
	@Id
	private String id;
	
	@Indexed
	private String ingredientName;

	public Ingredient(String ingredientName) {
		this.ingredientName = ingredientName;
	}	

	public Ingredient(String _id, String ingredientName) {
		this(ingredientName);
		this.id = _id;		
	}
	
	protected Ingredient() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
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
		Ingredient other = (Ingredient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ingredientName == null) {
			if (other.ingredientName != null)
				return false;
		} else if (!ingredientName.equals(other.ingredientName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ingredient [_id=" + id + ", ingredientName=" + ingredientName + "]";
	}
}
