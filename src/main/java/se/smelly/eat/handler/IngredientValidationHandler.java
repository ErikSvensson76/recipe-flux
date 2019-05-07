package se.smelly.eat.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import se.smelly.eat.form.IngredientForm;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.service.IngredientService;

@Component
public class IngredientValidationHandler extends AbstractValidationHandler<IngredientForm, Validator>{
	
	private IngredientService ingredientService;

	@Autowired
	private IngredientValidationHandler(Validator validator, IngredientService ingredientService) {
		super(IngredientForm.class, validator);	
		this.ingredientService = ingredientService;
	}

	@Override
	protected Mono<ServerResponse> processPost(IngredientForm validBody, ServerRequest originalRequest) {
		return ServerResponse.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(ingredientService.create(Mono.just(validBody)), Ingredient.class);
	}

	@Override
	protected Mono<ServerResponse> processPut(IngredientForm validBody, ServerRequest originalRequest) {
		Mono<Ingredient> updated =  Mono.just(validBody)
				.flatMap(ingredientForm -> {							
					Mono<Ingredient> updatedMono = ingredientService.findById(originalRequest.pathVariable("id"))
							.flatMap(currentIngredient ->{
								currentIngredient.setIngredientName(ingredientForm.getIngredientName());
								return ingredientService.save(currentIngredient);
							});
					return updatedMono;
				});
		
		return updated.flatMap(updatedIngredient -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(updatedIngredient)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	

}
