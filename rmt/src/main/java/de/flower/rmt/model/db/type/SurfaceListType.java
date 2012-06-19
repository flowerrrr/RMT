package de.flower.rmt.model.db.type;

/**
 * @author flowerrrr
 */
public class SurfaceListType extends AbstractListType<Surface> {

    @Override
    public String toString(final Surface object) {
        return object.toString();
    }

    @Override
    public Surface fromString(final String string) {
        return Surface.valueOf(string);
    }
}
