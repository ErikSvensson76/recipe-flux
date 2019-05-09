package se.smelly.eat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import se.smelly.eat.models.RecipeCategory;
import se.smelly.eat.service.RecipeCategoryService;

@Component
public class CategoryHandler {
	
	private RecipeCategoryService recipeCategoryService;

	@Autowired
	public CategoryHandler(RecipeCategoryService recipeCategoryService) {
		this.recipeCategoryService = recipeCategoryService;
	}
	
	public Mono<ServerResponse> getAllCategories(ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(recipeCategoryService.findAll(), RecipeCategory.class);
	}
	
	

}
