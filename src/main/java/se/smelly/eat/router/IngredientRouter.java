package se.smelly.eat.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import se.smelly.eat.handler.IngredientHandler;
import se.smelly.eat.handler.IngredientValidationHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class IngredientRouter {
	
	@Bean
	public RouterFunction<ServerResponse> ingredientRoute(@Autowired IngredientHandler ingredientHandler, @Autowired IngredientValidationHandler validationHandler){
		return RouterFunctions
				.route(GET("/ingredient")
						.and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::getAllIngredients)
				.andRoute(GET("/ingredient/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::getById)
				.andRoute(POST("/ingredient").and(accept(MediaType.APPLICATION_JSON_UTF8)), validationHandler::handlePostRequest)
				.andRoute(DELETE("/ingredient/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), ingredientHandler::delete)
				.andRoute(PUT("/ingredient/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), validationHandler::handlePutRequest);
	}

}
