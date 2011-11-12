package de.flower.common.validation.unique;

import de.flower.common.model.IEntity;
import de.flower.common.validation.unique.impl.IColumnResolver;
import de.flower.common.validation.unique.impl.IRowCountChecker;
import de.flower.common.validation.unique.impl.UniqueDef;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;
import java.util.List;

import static org.mockito.Mockito.*;

public class UniqueValidatorTest {

    private IColumnResolver columnResolver;
    private IRowCountChecker rowCountChecker;
    private UniqueValidator validator;
    private IEntity entity;
    private ConstraintValidatorContext context;
    private UniqueDef constraintDef;

    @BeforeMethod
    public void init() {
        columnResolver = mock(IColumnResolver.class);
        rowCountChecker = mock(IRowCountChecker.class);
        entity = mock(IEntity.class);
        context = mock(ConstraintValidatorContext.class);

        constraintDef = new UniqueDef("foo", new String[] { "name" });
        validator = new UniqueValidator(columnResolver, rowCountChecker);
        ReflectionTestUtils.setField(validator, "constraintDef", constraintDef);
    }

    @Test
    public final void testIsValidTrue() {
        when(rowCountChecker.rowCount(any(IEntity.class), (List<String>)any())).thenReturn(0L);
        final boolean valid = validator.isValid(entity, context);
        verify(context, never()).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public final void testIsValidFalse() {
        // some mockup needed
        when(rowCountChecker.rowCount(any(IEntity.class), (List<String>)any())).thenReturn(10L);
        final ConstraintViolationBuilder violationBuilder = mock(ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        final NodeBuilderDefinedContext nbdc = mock(NodeBuilderDefinedContext.class);
        when(violationBuilder.addNode(anyString())).thenReturn(nbdc);
        when(nbdc.addConstraintViolation()).thenReturn(context);

        // now finally we can call the method under test
        final boolean valid = validator.isValid(entity, context);
        verify(context, times(1)).buildConstraintViolationWithTemplate("{de.flower.common.validation.constraints.unique.message." + constraintDef.getName() + "}");
    }
}
