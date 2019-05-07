package se.smelly.eat.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.IngredientForm;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.models.MeasuredIngredient;
import se.smelly.eat.models.Measurement;

public interface IngredientService {

	Mono<Void> delete(String id);

	Mono<Ingredient> save(Ingredient toSave);

	Flux<Ingredient> findAll();

	Mono<Ingredient> findById(String id);

	Flux<Ingredient> findByIngredientNameStartWithIgnoreCase(String ingredientName);

	Mono<Ingredient> create(Mono<IngredientForm> form);

	Mono<MeasuredIngredient> create(String ingredientId, Measurement measurement, double amount);

}
