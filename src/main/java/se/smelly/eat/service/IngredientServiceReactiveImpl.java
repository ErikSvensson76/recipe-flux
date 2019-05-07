package se.smelly.eat.service;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.IngredientForm;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.models.MeasuredIngredient;
import se.smelly.eat.models.Measurement;
import se.smelly.eat.mongorepos.IngredientRepo;

@Service
public class IngredientServiceReactiveImpl implements IngredientService{
	
	private IngredientRepo repo;

	@Autowired
	public IngredientServiceReactiveImpl(IngredientRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Mono<Ingredient> create(Mono<IngredientForm> form){
		return form.flatMap(x -> repo.save(new Ingredient(x.getIngredientName())));
	}
	
	@Override
	public Flux<Ingredient> findByIngredientNameStartWithIgnoreCase(String ingredientName){
		Comparator<Ingredient> orderByLength = (o1, o2) -> o1.getIngredientName().length() - o2.getIngredientName().length(); 
		
		return repo.findByIngredientNameStartingWithIgnoreCase(ingredientName)
				.sort(orderByLength.reversed());
	}
	
	@Override
	public Mono<Ingredient> findById(String id){
		return repo.findById(id);
	}
	
	@Override
	public Flux<Ingredient> findAll(){
		return repo.findAll();
	}
	
	@Override
	public Mono<Ingredient> save(Ingredient toSave){
		return repo.save(toSave);
	}
	
	@Override
	public Mono<Void> delete(String id){
		return repo.deleteById(id);
	}
	
	@Override
	public Mono<MeasuredIngredient> create(String ingredientId, Measurement measurement, double amount){
		Mono<Ingredient> ingredientMono = repo.findById(ingredientId);
		
		return Mono.zip(ingredientMono, Mono.just(measurement), Mono.just(amount))
				.map(tuple -> new MeasuredIngredient(tuple.getT1(), tuple.getT2(), tuple.getT3()));
	}
}
