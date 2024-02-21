package com.example.assertj.conversion;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.AssertFactory;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactory;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.MapAssert;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;

/**
 *
 * @author Stephane Nicoll
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ValueType<T, ASSERT extends AbstractAssert<?, ?>> extends InstanceOfAssertFactory<T, ASSERT> {

	/**
	 * A {@link ValueType} implementation for {@linkplain String strings}.
	 */
	public static final ValueType<String, AbstractStringAssert<?>> STRING = of(String.class, Assertions::assertThat);

	private final Type type;

	private ValueType(Type type, AssertFactory<T, ASSERT> assertFactory) {
		super(rawType(type), assertFactory);
		this.type = type;
	}

	private static <T> Class<T> rawType(Type type) {
		return (Class<T>) ResolvableType.forType(type).toClass();
	}

	public static <T, A extends AbstractAssert<?, T>> ValueType<T, A> of(Class<T> objectType,
			AssertFactory<T, A> assertFactory) {
		return new ValueType<>(objectType, assertFactory);
	}

	public static <T, A extends AbstractAssert<?, T>> ValueType<T, A> of(ParameterizedTypeReference<T> objectType,
			AssertFactory<T, A> assertFactory) {
		return new ValueType<>(objectType.getType(), assertFactory);
	}

	public static <E> ValueType<List, ListAssert<E>> listOf(Class<E> elementType) {
		return listOf("a list of '%s".formatted(elementType.getName()), elementType);
	}

	public static <K, V> ValueType<Map, MapAssert<K, V>> mapOf(Class<K> keyType, Class<V> valueType) {
		return mapOf("a map with '%s' keys and '%s' values".formatted(keyType.getName(),
				valueType.getName()), keyType, valueType);
	}

	public ASSERT createAssert(Object actual, Function<Type, T> convertFactory) {
		T convertedValue = convertFactory.apply(this.type);
		return createAssert(convertedValue);
	}

	@SuppressWarnings("unused")
	private static <E> ValueType<List, ListAssert<E>> listOf(String description, Class<E> elementType) {
		ResolvableType resolvableType = ResolvableType.forClassWithGenerics(List.class, elementType);
		return new ValueType<>(resolvableType.getType(), Assertions::<E>assertThat);
	}

	@SuppressWarnings("unused")
	private static <K, V> ValueType<Map, MapAssert<K, V>> mapOf(String description, Class<K> keyType, Class<V> valueType) {
		ResolvableType resolvableType = ResolvableType.forClassWithGenerics(Map.class, keyType, valueType);
		return new ValueType<>(resolvableType.getType(), Assertions::<K, V>assertThat);
	}

}
