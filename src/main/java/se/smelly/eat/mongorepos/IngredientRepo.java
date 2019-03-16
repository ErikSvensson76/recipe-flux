package se.smelly.eat.mongorepos;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import se.smelly.eat.models.Ingredient;

public interface IngredientRepo extends ReactiveMongoRepository<Ingredient, String>{
	
	@Query("{ 'ingredientName' : ?0}")
	Mono<Ingredient> findFirstByIngredientName(String ingredientName);
	
		

}
