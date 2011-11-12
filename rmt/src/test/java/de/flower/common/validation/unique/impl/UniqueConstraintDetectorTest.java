package de.flower.common.validation.unique.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class UniqueConstraintDetectorTest {

    @Test
    public void testGetColumnNames() {
        final String[] expectedColumnNames = new String[] { "name", "account_id" };
        final String[] columnNames = UniqueConstraintDetector.getColumnNames("c1", TestEntity.class);
        Assert.assertEquals(expectedColumnNames, columnNames);
    }

    @Table(uniqueConstraints = {
            @UniqueConstraint(name = "c1", columnNames = { "name", "account_id" }),
            @UniqueConstraint(name = "c2", columnNames = { "suId" })
            }
    )
    private static class TestEntity {
    }

}
