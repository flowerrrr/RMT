package de.flower.rmt.model.db.type;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;


public class SurfaceListTypeTest {

    @Test
    public void testDisassembleAssemble() {
        SurfaceListType type = new SurfaceListType();
        List<Surface> list = Arrays.asList(Surface.NATURAL_GRASS, Surface.ARTIFICIAL_GRASS);
        String string = type.list2String(list);
        List<Surface> list2 = type.string2List(string);
        assertEquals(list2, list);
    }
}
