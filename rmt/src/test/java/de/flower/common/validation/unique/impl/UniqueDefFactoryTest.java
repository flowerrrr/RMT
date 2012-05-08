package de.flower.common.validation.unique.impl;

import de.flower.common.validation.unique.Unique;
import org.testng.annotations.Test;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Class tests all sorts of @Unique usages and missusages.
 */
public class UniqueDefFactoryTest {

    private final IColumnResolver columnResolver = new MockColumnResolver();

    private final List<String> expectedAttributes = Arrays.asList(new String[] { "name", "email" });

    @Test
    public void testParseTableUniqueConstraint() {
        @Table(uniqueConstraints = { @UniqueConstraint(name = "c1", columnNames = { "name", "email" }) })
        @Unique(name = "c1", clazz = TestEntity.class, groups = { Void.class })
        final class TestEntity {
        }

        final Unique constraint = TestEntity.class.getAnnotation(Unique.class);
        final UniqueDef uniqueDef = UniqueDefFactory.parseConstraint(constraint, columnResolver);
        assertEquals("c1", uniqueDef.getName());
        assertEquals(expectedAttributes, uniqueDef.getAttributeNames());
    }

    @Test
    public void testParseConstraintWithAttributeNames() {
        @Unique(name = "c1", attributeNames = { "name", "email" }, clazz = TestEntity.class, groups = { Void.class })
        final class TestEntity {
        }

        final Unique constraint = TestEntity.class.getAnnotation(Unique.class);
        final UniqueDef uniqueDef = UniqueDefFactory.parseConstraint(constraint, columnResolver);
        assertEquals("c1", uniqueDef.getName());
        assertEquals(expectedAttributes, uniqueDef.getAttributeNames());
    }

    @Test
    public void testParseConstraintWithoutName() {
        @Unique(attributeNames = { "name", "email" }, clazz = TestEntity.class, groups = { Void.class })
        final class TestEntity {
        }

        final Unique constraint = TestEntity.class.getAnnotation(Unique.class);
        final UniqueDef uniqueDef = UniqueDefFactory.parseConstraint(constraint, columnResolver);
        assertEquals("uc_name_email", uniqueDef.getName());
        assertEquals(expectedAttributes, uniqueDef.getAttributeNames());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCheckMissingArguments() {
        @Unique(clazz = TestEntity.class, groups = { Void.class })
        final class TestEntity {
        }

        final Unique constraint = TestEntity.class.getAnnotation(Unique.class);
        final UniqueDef uniqueDef = UniqueDefFactory.parseConstraint(constraint, columnResolver);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testCheckUnknownConstraint() {
        @Unique(name = "unknownConstraint", clazz = TestEntity.class, groups = { Void.class })
        final class TestEntity {
        }

        final Unique constraint = TestEntity.class.getAnnotation(Unique.class);
        final UniqueDef uniqueDef = UniqueDefFactory.parseConstraint(constraint, columnResolver);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCheckMissingGroup() {
        @Unique(attributeNames = { "name" }, clazz = TestEntity.class)
        final class TestEntity {
        }

        final Unique constraint = TestEntity.class.getAnnotation(Unique.class);
        final UniqueDef uniqueDef = UniqueDefFactory.parseConstraint(constraint, columnResolver);
    }

    private static class MockColumnResolver implements IColumnResolver {

        @Override
        public String[] mapColumns2Attributes(final Class<?> entityClass, final String[] columnNames) {
            return columnNames;
        }

    }

}
