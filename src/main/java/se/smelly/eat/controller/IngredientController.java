package se.smelly.eat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.IngredientForm;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.models.Measurement;
import se.smelly.eat.service.IngredientService;

@RestController
public class IngredientController {
	
	private IngredientService ingredientService;

	@Autowired
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}
	
	@GetMapping("/ingredient")
	public ResponseEntity<Flux<Ingredient>> getAll() {
		return ResponseEntity.ok().body(ingredientService.findAll());
	}
	
	@PostMapping("/ingredient")
	public ResponseEntity<Mono<Ingredient>> create(@RequestBody IngredientForm form){
		Mono<IngredientForm> formMono = Mono.just(form);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(Mono.from(ingredientService.create(formMono)));		
	}	
	
	@GetMapping("/ingredient/{id}")
	public ResponseEntity<Mono<Ingredient>> getById(@PathVariable("id") String id){
		Mono<Ingredient> resultMono = Mono.from(ingredientService.findById(id));		
		return ResponseEntity.ok().body(resultMono);	
	}
	
	@PutMapping("/ingredient/{id}")
	public ResponseEntity<Mono<Ingredient>> update(@PathVariable("id") String id, @RequestBody Ingredient ingredient){
		return ResponseEntity.ok(ingredientService.save(ingredient));
	}
	
	@GetMapping("/ingredient/measurement")
	public ResponseEntity<Flux<Measurement>> getMeasurements(){
		return ResponseEntity.ok().body(Flux.fromArray(Measurement.values()));
	}
}
