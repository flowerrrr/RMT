package de.flower.common.ui;

import com.thoughtworks.xstream.XStream;
import de.flower.common.util.Check;
import org.apache.wicket.serialize.ISerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class LoggingSerializer implements ISerializer {

    private final static Logger log = LoggerFactory.getLogger(LoggingSerializer.class);

    private ISerializer serializer;

    private XStream xstream = new XStream();

    public LoggingSerializer(ISerializer serializer) {
        this.serializer = Check.notNull(serializer);
    }

    @Override
    public byte[] serialize(Object object) {
        byte[] data = serializer.serialize(object);
        log.debug("Serialized object size: " + data.length);
        if (log.isTraceEnabled()) {
            log.trace(xstream.toXML(object));
        }
        return data;
    }

    @Override
    public Object deserialize(byte[] data) {
        return serializer.deserialize(data);
    }
}
