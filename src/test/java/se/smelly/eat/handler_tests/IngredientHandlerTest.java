package se.smelly.eat.handler_tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.IngredientForm;
import se.smelly.eat.models.Ingredient;
import se.smelly.eat.mongorepos.IngredientRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
public class IngredientHandlerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Autowired
	private IngredientRepo ingredientRepo;
	
	public List<Ingredient> data(){
		return Arrays.asList(
				new Ingredient("Spagetti"),
				new Ingredient("Tomatoes"),
				new Ingredient("Spam"),
				new Ingredient("testId", "Test")
				);
	}
	
	@Before
	public void setup() {
		ingredientRepo.deleteAll()
			.thenMany(Flux.fromIterable(data()))
			.flatMap(ingredientRepo::save)
			.blockLast();
	}
	
	@Test
	public void getAllIngredients_ok() {
		webTestClient.get().uri("/ingredient")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Ingredient.class)
			.hasSize(4);			
	}
	
	@Test
	public void getById_ok() {
		webTestClient.get().uri("/ingredient/{id}", "testId")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody()
			.jsonPath("$._id", "testId");			
	}
	
	@Test
	public void getById_notFound() {
		webTestClient.get().uri("/ingredient/{id}", "das")
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	public void create_status_created_and_return_ingredient() {
		IngredientForm formData = new IngredientForm("Ham");
		
		webTestClient.post().uri("/ingredient")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(formData), IngredientForm.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.ingredientName").isEqualTo("Ham");			
	}
	
	@Test
	public void create_status_badRequest() {
		IngredientForm formData = new IngredientForm("H");
		
		webTestClient.post().uri("/ingredient")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(formData), IngredientForm.class)
			.exchange()
			.expectStatus().isBadRequest();						
	}
	
	@Test
	public void delete_ingredient_statusOk() {
		webTestClient.delete().uri("/ingredient/{id}", "testId")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Void.class);
		
	}
	
	@Test
	public void update_ingredient_statusOk() {
		IngredientForm updated = new IngredientForm("UpdatedName");
		
		webTestClient.put().uri("/ingredient/{id}", "testId")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(updated), IngredientForm.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.ingredientName").isEqualTo("UpdatedName");
	}
	
	@Test
	public void update_ingredient_badRequest() {
		IngredientForm updated = new IngredientForm("U");
		
		webTestClient.put().uri("/ingredient/{id}", "testId")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(updated), IngredientForm.class)
			.exchange()
			.expectStatus().isBadRequest();			
	}
	
	@Test
	public void update_ingredient_notFound() {
		IngredientForm updated = new IngredientForm("UpdatedName");
		
		webTestClient.put().uri("/ingredient/{id}", "xxx")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(updated), IngredientForm.class)
			.exchange()
			.expectStatus().isNotFound();					
	}
	
	@Test
	public void getByName_return_flux_of_two() {		
		
		String requestAttribute = "s";
		
		webTestClient.get().uri("/ingredient/find/{name}", requestAttribute)			
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Ingredient.class)
			.hasSize(2);					
	}
}
