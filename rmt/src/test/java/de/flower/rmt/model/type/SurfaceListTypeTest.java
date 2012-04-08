package de.flower.rmt.model.type;

import de.flower.rmt.model.Surface;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
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
