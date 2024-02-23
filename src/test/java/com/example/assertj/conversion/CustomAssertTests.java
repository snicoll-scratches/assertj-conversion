package com.example.assertj.conversion;

import java.util.List;

import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import org.springframework.core.ParameterizedTypeReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Stephane Nicoll
 */
class CustomAssertTests {


	@Test
	void convertToUsingInstanceOfAssertFactory() {
		assertThat(forValue(123)).convertTo(InstanceOfAssertFactories.STRING).startsWith("1");
	}

	@Test
	void convertToUsingValueType() {
		assertThat(forValue(123)).convertTo(ValueType.STRING).startsWith("1");
	}

	@Test
	void convertToListOfObject() {
		Customer[] customers = new Customer[] { new Customer("123", "John", "Smith", 42) };

		assertThat(forValue(customers)).convertTo(ValueType.listOf(Customer.class))
				.hasSize(1).contains(new Customer("123", "John", "Smith", 42));
	}

	@Test
	void convertToListOfObjectUsingGenericType() {
		Customer[] customers = new Customer[] { new Customer("123", "John", "Smith", 42) };

		assertThat(forValue(customers)).convertTo(ValueType.of(new ParameterizedTypeReference<List<Customer>>() {}, value -> Assertions.assertThat((List<Customer>) value)))
				.hasSize(1).contains(new Customer("123", "John", "Smith", 42));
	}


	private AssertProvider<CustomAssert> forValue(Object value) {
		return () -> new CustomAssert(value);
	}

}
