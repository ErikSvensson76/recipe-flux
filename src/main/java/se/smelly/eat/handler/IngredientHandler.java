package se.smelly.eat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import se.smelly.eat.form.IngredientForm;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.service.IngredientService;
import static org.springframework.web.reactive.function.BodyInserters.*;

@Component
public class IngredientHandler {
	
	private IngredientService ingredientService;

	@Autowired
	public IngredientHandler(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}
	
	public Mono<ServerResponse> getAllIngredients(ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(ingredientService.findAll(), Ingredient.class);				
	}
	
	public Mono<ServerResponse> getById(ServerRequest serverRequest){
		
		String id = serverRequest.pathVariable("id");
		Mono<Ingredient> ingredientMono = ingredientService.findById(id);
		
		return ingredientMono.flatMap(ingredient -> ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(fromObject(ingredient)))
				.switchIfEmpty(ServerResponse.notFound().build());								
	}
	
	public Mono<ServerResponse> create(ServerRequest serverRequest){				
		return ServerResponse.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(ingredientService.create(serverRequest.bodyToMono(IngredientForm.class)), Ingredient.class);				
	}
	
	public Mono<ServerResponse> delete(ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(ingredientService.delete(serverRequest.pathVariable("id")), Void.class);						
	}
	
	public Mono<ServerResponse> update(ServerRequest serverRequest){		
		Mono<Ingredient> updated =  serverRequest.bodyToMono(Ingredient.class)
				.flatMap(ingredient -> {							
					Mono<Ingredient> updatedMono = ingredientService.findById(serverRequest.pathVariable("id"))
							.flatMap(currentIngredient ->{
								currentIngredient.setIngredientName(ingredient.getIngredientName());
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
