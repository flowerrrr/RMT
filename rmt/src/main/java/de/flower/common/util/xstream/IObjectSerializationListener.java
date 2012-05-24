package de.flower.common.util.xstream;

/**
 * @author flowerrrr
 */
public interface IObjectSerializationListener {

    /**
     * Called by {@link ClassEmittingReflectionConverter} for each object in the serialized object graph.
     * @param object
     */
    void notify(Object object);

}
