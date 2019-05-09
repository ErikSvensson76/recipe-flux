package se.smelly.eat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.smelly.eat.form.CategoryForm;
import se.smelly.eat.models.RecipeCategory;
import se.smelly.eat.mongorepos.RecipeCategoryRepo;

@Service
public class RecipeCategoryServiceReactiveImpl implements RecipeCategoryService {
	
	private RecipeCategoryRepo recipeCategoryRepo;

	@Autowired
	public RecipeCategoryServiceReactiveImpl(RecipeCategoryRepo recipeCategoryRepo) {
		this.recipeCategoryRepo = recipeCategoryRepo;
	}
	
	@Override
	public Mono<RecipeCategory> create(Mono<CategoryForm> form){
		return form.
				flatMap(x -> recipeCategoryRepo.save(new RecipeCategory(x.getCategoryName())));
	}
	
	@Override
	public Flux<RecipeCategory> findByCategoryName(String categoryName){
		return recipeCategoryRepo.findByCategoryNameStartingWithIgnoreCase(categoryName);		
	}
	
	@Override
	public Mono<RecipeCategory> save(RecipeCategory category){
		return recipeCategoryRepo.save(category);
	}
	
	@Override
	public Mono<Void> delete(String id){
		return recipeCategoryRepo.deleteById(id);
	}
	
	@Override
	public Mono<RecipeCategory> findById(String id){
		return recipeCategoryRepo.findById(id);
	}
	
	@Override
	public Flux<RecipeCategory> findAll(){
		return recipeCategoryRepo.findAll();
	}
}
