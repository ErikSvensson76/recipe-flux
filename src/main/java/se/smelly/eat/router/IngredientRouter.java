package se.smelly.eat.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import se.smelly.eat.handler.IngredientHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class IngredientRouter {
	
	@Bean
	public RouterFunction<ServerResponse> ingredientRoute(IngredientHandler ingredientHandler){
		return RouterFunctions
				.route(GET("/ingredient")
						.and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::getAllIngredients)
				.andRoute(GET("/ingredient/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::getById)
				.andRoute(POST("/ingredient").and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::create)
				.andRoute(DELETE("/ingredient/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::delete)
				.andRoute(PUT("/ingredient/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::update);
	}

}
