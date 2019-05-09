package se.smelly.eat.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.util.Comparator;
import java.util.List;

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
	
	public Mono<ServerResponse> getById(ServerRequest serverRequest){
		Mono<RecipeCategory> categoryMono = recipeCategoryService.findById(serverRequest.pathVariable("id"));
		
		return categoryMono.flatMap(category -> ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(fromObject(category)))
				.switchIfEmpty(ServerResponse.notFound().build());		
	}
	
	public Mono<ServerResponse> delete(ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(recipeCategoryService.delete(serverRequest.pathVariable("id")), Void.class);
	}
	
	public Mono<ServerResponse> getByName(ServerRequest serverRequest){
		Comparator<RecipeCategory> orderByLength = (cat1, cat2) -> cat1.getCategoryName().length() - cat2.getCategoryName().length();
		
		String name = serverRequest.pathVariable("name");
		Mono<List<RecipeCategory>> result = recipeCategoryService.findByCategoryName(name)
				.collectSortedList(orderByLength.reversed());
		
		return result.flatMap(list -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(list)));		
	}
	
	

}
