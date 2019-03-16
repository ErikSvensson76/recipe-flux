package se.smelly.eat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
		ingredientRepo.deleteAll().log().subscribe();
		
		
				
		
		 
		
	}

}
