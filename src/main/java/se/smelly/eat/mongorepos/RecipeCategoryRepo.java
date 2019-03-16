package se.smelly.eat.mongorepos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import se.smelly.eat.models.RecipeCategory;

public interface RecipeCategoryRepo extends ReactiveMongoRepository<RecipeCategory, String>{
	
	Flux<RecipeCategory> findByCategoryName(String categoryName);

}
