package se.smelly.eat.router;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import se.smelly.eat.handler.CategoryHandler;
import se.smelly.eat.handler.IngredientHandler;
import se.smelly.eat.handler.IngredientValidationHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class CategoryRouter {
	
	@Bean
	public RouterFunction<ServerResponse> categoryRoute(@Autowired CategoryHandler categoryHandler){
		return RouterFunctions.route(GET("/category").and(accept(MediaType.APPLICATION_JSON_UTF8)), categoryHandler::getAllCategories);
	}

}
