package de.galan.commons.func.exceptional;

/**
 * Functional Interface similar to Function, but could throw an Exception.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface ExceptionalFunction<T, R> {

	R apply(T t) throws Exception;

}
