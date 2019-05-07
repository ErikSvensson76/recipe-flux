package se.smelly.eat.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public abstract class AbstractValidationHandler<T, U extends Validator> {
	
	private final Class<T> validationClass;
	
	private final U validator;
	
	protected AbstractValidationHandler(Class<T> validationClass, U validator) {
		this.validationClass = validationClass;
		this.validator = validator;
	}
	
	abstract protected Mono<ServerResponse> processPost(T validBody, final ServerRequest originalRequest);
	abstract protected Mono<ServerResponse> processPut(T validBody, final ServerRequest originalRequest);
	
	public final Mono<ServerResponse> handlePostRequest(final ServerRequest request){
		return request.bodyToMono(this.validationClass)
				.flatMap(body -> {
					
					Errors errors = new BeanPropertyBindingResult(body, this.validationClass.getName());
					this.validator.validate(body, errors);
					
					if(errors == null || errors.getAllErrors().isEmpty()) {
						return processPost(body, request);
					}else {
						return onValidationErrors(errors, body, request);
					}
					
				});
	}
	
	public final Mono<ServerResponse> handlePutRequest(final ServerRequest request){
		return request.bodyToMono(this.validationClass)
				.flatMap(body -> {
					
					Errors errors = new BeanPropertyBindingResult(body, this.validationClass.getName());
					this.validator.validate(body, errors);
					
					if(errors == null || errors.getAllErrors().isEmpty()) {
						return processPut(body, request);
					}else {
						return onValidationErrors(errors, body, request);
					}
					
				});
	}	

	protected Mono<ServerResponse> onValidationErrors(Errors errors, T bodyWithErrors, ServerRequest request) {
		
		Mono<Map<String, Object>> map = Mono.fromSupplier(HashMap<String, Object>::new)
			.flatMap(errorMap ->{
				errors.getAllErrors().forEach(error ->{
					String fieldName = ((FieldError) error).getField();
					String message = error.getDefaultMessage();
					errorMap.put(fieldName, message);
				});
				return Mono.just(errorMap);
			});
		
		return ServerResponse.badRequest().body(Mono.from(map), Map.class);
	}

}
