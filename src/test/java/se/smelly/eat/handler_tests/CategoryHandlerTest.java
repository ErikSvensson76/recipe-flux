package se.smelly.eat.handler_tests;

import java.util.ArrayList;
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
import se.smelly.eat.models.RecipeCategory;
import se.smelly.eat.mongorepos.RecipeCategoryRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
public class CategoryHandlerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Autowired
	private RecipeCategoryRepo categoryRepo;
	
	public List<RecipeCategory> data(){
		return new ArrayList<>(Arrays.asList(
				new RecipeCategory("testId", "Spicy"),
				new RecipeCategory("Grill"),
				new RecipeCategory("Weekend"),
				new RecipeCategory("Weekend dinner")
				));
	}
	
	@Before
	public void setup() {
		categoryRepo.deleteAll()
			.thenMany(Flux.fromIterable(data()))
			.flatMap(categoryRepo::save)
			.blockLast();
	}
	
	@Test
	public void getAllCategories_ok() {
		webTestClient.get().uri("/category")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(RecipeCategory.class)
			.hasSize(4);
	}
	
	@Test
	public void getById_ok() {
		webTestClient.get().uri("/category/{id}", "testId")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody()
			.jsonPath("$._id").isEqualTo("testId")
			.jsonPath("$.categoryName").isEqualTo("Spicy");
	}
	
	@Test
	public void getById_notFound() {
		webTestClient.get().uri("/category/{id}", "xxx")
		.exchange()
		.expectStatus().isNotFound();
	}
	
	@Test
	public void delete_ok() {
		webTestClient.delete().uri("/category/{id}", "testId")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Void.class);		
	}
	
	@Test
	public void getByName_return_flux_of_two() {
		webTestClient.get().uri("/category/find/{name}", "week")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(RecipeCategory.class)
			.hasSize(2);			
	}
	
	

}
