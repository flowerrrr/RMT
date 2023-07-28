package de.flower.common.ui.serialize;

import org.apache.wicket.serialize.ISerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class SerializerWrapper implements ISerializer {

    private ISerializer serializer;

    private List<PageSerializationValidatorListener> listeners = new ArrayList();

    public SerializerWrapper(ISerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public byte[] serialize(Object object) {
        byte[] data = serializer.serialize(object);
        notify(object, data);
        return data;
    }

    private void notify(Object object, byte[] data) {
        for (PageSerializationValidatorListener listener : listeners) {
            listener.notify(object, data);
        }
    }

    @Override
    public Object deserialize(byte[] data) {
        return serializer.deserialize(data);
    }

    public void addListener(PageSerializationValidatorListener listener) {
        listeners.add(listener);
    }
}
