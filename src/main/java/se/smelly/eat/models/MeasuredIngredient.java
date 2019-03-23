package se.smelly.eat.models;

import org.springframework.data.mongodb.core.index.Indexed;

public class MeasuredIngredient {
	

	@Indexed
	private Ingredient ingredient;
	private Measurement measurement;
	private double amount;
	
	public MeasuredIngredient(Ingredient ingredient, Measurement measurement, double amount) {
		this.ingredient = ingredient;
		this.measurement = measurement;
		this.amount = amount;
	}	

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
		result = prime * result + ((measurement == null) ? 0 : measurement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeasuredIngredient other = (MeasuredIngredient) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (ingredient == null) {
			if (other.ingredient != null)
				return false;
		} else if (!ingredient.equals(other.ingredient))
			return false;
		if (measurement != other.measurement)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MeasuredIngredient [ingredient=" + ingredient + ", measurement=" + measurement
				+ ", amount=" + amount + "]";
	}
	
	
}
