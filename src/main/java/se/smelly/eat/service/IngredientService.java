package se.smelly.eat.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.IngredientForm;
import se.smelly.eat.models.Ingredient;

public interface IngredientService {

	Mono<Void> delete(Ingredient ingredient);

	Mono<Ingredient> save(Ingredient toSave);

	Flux<Ingredient> findAll();

	Mono<Ingredient> findById(String id);

	Flux<Ingredient> findByIngredientNameStartWith(String ingredientName);

	Mono<Ingredient> create(Mono<IngredientForm> form);

}
