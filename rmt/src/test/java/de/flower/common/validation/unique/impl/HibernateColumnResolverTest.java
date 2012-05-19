package de.flower.common.validation.unique.impl;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class HibernateColumnResolverTest extends AbstractRMTIntegrationTests {

    @Autowired
    private HibernateColumnResolver columnResolver;

    @Test
    public final void mapColumns2Attributes() {
        final String[] columnNames = new String[] { "name", "club_id" };
        final String[] expected = new String[] { "name", "club" };
        assertEquals(columnResolver.mapColumns2Attributes(Team.class, columnNames), expected);
    }


}
