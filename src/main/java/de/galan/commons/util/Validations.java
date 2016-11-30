package de.galan.commons.util;

import static java.util.stream.Collectors.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;


/**
 * Validates an object using the javax validation api.
 */
public class Validations {

	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();


	public static void validate(Object object) {
		Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(object);
		if (!violations.isEmpty()) {
			String message = violations.stream()
				.map(violation -> violation.getPropertyPath() + " (" + violation.getMessage() + ")")
				.collect(joining(", "));
			throw new ConstraintViolationException(message, violations);
		}
	}

}
