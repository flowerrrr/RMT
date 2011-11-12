package de.flower.common.validation.unique.impl;

import de.flower.common.validation.unique.impl.NamingConventionColumnResolver;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class NamingConventionColumnResolverTest {

    @Test
    public final void mapColumns2Attributes() {
        final String[] columnNames = new String[] { "email", "ACCOUNT_ID" };
        final String[] expected = new String[] { "email", "account" };
        assertEquals(new NamingConventionColumnResolver().mapColumns2Attributes(null, columnNames), expected);
    }


}
