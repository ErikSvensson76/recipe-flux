package se.smelly.eat.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.CategoryForm;
import se.smelly.eat.models.RecipeCategory;

public interface RecipeCategoryService {

	Mono<RecipeCategory> create(Mono<CategoryForm> form);

	Flux<RecipeCategory> findByCategoryName(String categoryName);

	Mono<RecipeCategory> save(RecipeCategory category);

	Mono<Void> delete(String id);

	Mono<RecipeCategory> findById(String id);

	Flux<RecipeCategory> findAll();

}