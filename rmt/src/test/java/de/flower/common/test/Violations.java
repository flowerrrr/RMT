package de.flower.common.test;

import javax.validation.ConstraintViolation;
import java.lang.annotation.Annotation;
import java.util.Set;

import static org.testng.Assert.*;

public class Violations {

    @SuppressWarnings("unchecked")
    public static void assertViolation(final String messageTemplate, final Set<?> violations) {
        for (final ConstraintViolation<?> violation : (Set<ConstraintViolation<?>>)violations) {
            if (equalsViolation(messageTemplate, violation)) {
                return;
            }
        }
        fail("Violation with messageTemplate [" + messageTemplate + "] not found in " + dump(violations));
    }

    @SuppressWarnings("unchecked")
    public static void assertViolation(final Class<? extends Annotation> constraintClass, final String propertyPath,
            final Set<?> violations) {
        for (final ConstraintViolation<?> violation : (Set<ConstraintViolation<?>>)violations) {
            if (equalsViolation(constraintClass, propertyPath, violation)) {
                return;
            }
        }
        fail("Violation [" + constraintClass + ", " + propertyPath + "] not found in " + dump(violations));
    }

    public static boolean equalsViolation(final Class<? extends Annotation> constraintClass, final String propertyPath,
            final ConstraintViolation<?> violation) {
        return propertyPath.equals(violation.getPropertyPath().toString())
                && constraintClass.equals(violation.getConstraintDescriptor().getAnnotation().annotationType());
    }

    public static boolean equalsViolation(final String messageTemplate, final ConstraintViolation<?> violation) {
        return messageTemplate.equals(violation.getMessageTemplate());
    }

    public static void assertViolation(final String messageTemplate, final ConstraintViolation<?> violation) {
        assertEquals(messageTemplate, violation.getMessageTemplate());
    }

    @SuppressWarnings("unchecked")
    public static String dump(final Set<?> violations) {
        String s = "Constraint violations:";
        for (final ConstraintViolation<?> violation : (Set<ConstraintViolation<?>>)violations) {
            s += "\n" + violation.toString();
        }
        return s;
    }




}
