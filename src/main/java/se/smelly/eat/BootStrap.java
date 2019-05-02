package se.smelly.eat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import se.smelly.eat.models.Ingredient;
import se.smelly.eat.mongorepos.IngredientRepo;

@Component
public class BootStrap implements CommandLineRunner{
	
	private IngredientRepo ingredientRepo;
	
	
	
	


	@Autowired
	public BootStrap(IngredientRepo ingredientRepo) {
		this.ingredientRepo = ingredientRepo;		
	}






	@Override
	public void run(String... args) throws Exception {		
		//seedIngredients();		
	}
	
	public void seedIngredients() {
		ingredientRepo.deleteAll().block();		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(new Ingredient("Tomatoes"));
		ingredients.add(new Ingredient("Potatoes"));
		ingredients.add(new Ingredient("Spagetti"));
		
		ingredientRepo.saveAll(ingredients).blockLast();
	}

}
