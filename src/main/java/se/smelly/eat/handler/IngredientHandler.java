package se.smelly.eat.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.service.IngredientService;

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
	
	public Mono<ServerResponse> delete(ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(ingredientService.delete(serverRequest.pathVariable("id")), Void.class);						
	}	
	
	public Mono<ServerResponse> getByName(ServerRequest serverRequest){
		
		String name = serverRequest.pathVariable("name");
		Mono<List<Ingredient>> result = ingredientService.findByIngredientNameStartWithIgnoreCase(name).collectList();
		
		return result.flatMap(list -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(list)));		
	}
}
