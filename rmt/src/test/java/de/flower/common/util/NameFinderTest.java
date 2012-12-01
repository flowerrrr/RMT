package de.flower.common.util;

import com.google.common.base.Predicate;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class NameFinderTest {

    @Test
    public void testGenerate() {
        String base = "base";
        String name = NameFinder.delete(base, new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                return true;
            }
        });
        assertEquals(name, "DELETED-" + base);

        // test iteration. let candidate #100 pass through
        name = NameFinder.delete(base, new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                return input.contains("100");
            }
        });
        assertEquals(name, "DELETED-" + base + "-100");
    }

    @Test
    public void testSuffixes() {
        String base = "Team";
        String name = NameFinder.newName(base, "A.B.C".split("\\."), new Predicate<String>() {

            @Override
            public boolean apply(final String input) {
                return true;
            }
        });
        assertEquals(name, base + " A");
    }
}
