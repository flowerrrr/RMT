package de.flower.common.ui.serialize;

/**
 * @author flowerrrr
 */
public interface ISerializerListener {

    void notify(Object object, byte[] data);
}
