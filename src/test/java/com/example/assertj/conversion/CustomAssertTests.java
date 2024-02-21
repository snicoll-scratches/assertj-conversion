package com.example.assertj.conversion;

import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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


	private AssertProvider<CustomAssert> forValue(Object value) {
		return () -> new CustomAssert(value);
	}

}
