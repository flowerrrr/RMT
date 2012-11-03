package de.flower.common.validation.unique;

import de.flower.common.model.db.entity.IEntity;
import de.flower.common.validation.unique.impl.IColumnResolver;
import de.flower.common.validation.unique.impl.IRowCountChecker;
import de.flower.common.validation.unique.impl.UniqueDef;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class UniqueValidatorTest {

    private IRowCountChecker rowCountChecker;
    private UniqueValidator validator;
    private IEntity entity;
    private ConstraintValidatorContext context;

    @BeforeMethod
    public void init() {
        final IColumnResolver columnResolver = mock(IColumnResolver.class);
        rowCountChecker = mock(IRowCountChecker.class);
        entity = mock(IEntity.class);
        when(entity.getId()).thenReturn(1L);
        context = mock(ConstraintValidatorContext.class);

        final UniqueDef constraintDef = new UniqueDef("foo", new String[]{"id"});
        validator = new UniqueValidator(columnResolver, rowCountChecker);
        ReflectionTestUtils.setField(validator, "constraintDef", constraintDef);
    }

    @Test
    public final void testIsValidTrue() {
        when(rowCountChecker.rowCount(any(IEntity.class), (List<String>)any())).thenReturn(0L);
        final boolean valid = validator.isValid(entity, context);
        assertTrue(valid);
    }

    @Test
    public final void testIsValidFalse() {
        // some mockup needed
        when(rowCountChecker.rowCount(any(IEntity.class), (List<String>)any())).thenReturn(10L);
        // now finally we can call the method under test
        final boolean valid = validator.isValid(entity, context);
        assertFalse(valid);
    }

    /**
     * Test for null values.
     */
    @Test
    public final void testValidateNull() {
        when(entity.getId()).thenReturn(null);
        final boolean valid = validator.isValid(entity, context);
        assertTrue(valid);
        verify(rowCountChecker, never()).rowCount(any(IEntity.class), anyList());
    }

}
