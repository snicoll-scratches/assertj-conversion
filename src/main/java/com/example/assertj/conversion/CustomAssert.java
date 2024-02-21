package com.example.assertj.conversion;

import java.lang.reflect.Type;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.InstanceOfAssertFactory;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

public class CustomAssert extends AbstractObjectAssert<CustomAssert, Object> {

	public CustomAssert(Object actual) {
		super(actual, CustomAssert.class);
	}

	public <T, ASSERT extends AbstractAssert<?, ?>> ASSERT convertTo(InstanceOfAssertFactory<T, ASSERT> factory) {
		// actual is expected to be of type T
		return factory.createAssert(this.actual);
	}

	public <T, ASSERT extends AbstractAssert<?, ?>> ASSERT convertTo(ValueType<T, ASSERT> factory) {
		return factory.createAssert(this.actual, this::convert);
	}


	@SuppressWarnings("unchecked")
	private <T> T convert(Type type) {
		// call a conversion service that convert actual to the requested type
		Object convert = DefaultConversionService.getSharedInstance().convert(
				this.actual, new TypeDescriptor(ResolvableType.forType(type), null, null));
		return (T) convert;
	}

}
