package se.smelly.eat.form;

public class CategoryForm {
	
		
	private String categoryName;

	public CategoryForm(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
