package de.flower.common.ui.serialize;

import com.thoughtworks.xstream.XStream;
import de.flower.common.util.xstream.ClassEmittingReflectionConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author flowerrrr
 */
public class LoggingSerializer implements ISerializerListener {

    private final static Logger log = LoggerFactory.getLogger(LoggingSerializer.class);

    private final static Logger xstreamLog = LoggerFactory.getLogger("xstream");

    private XStream xstream;

    private Filter filter;

    public LoggingSerializer(Filter filter) {
        xstream = new XStream();
        // by default xstream does not output the classname of serialized fields.
        xstream.registerConverter(new ClassEmittingReflectionConverter(xstream), XStream.PRIORITY_VERY_LOW);

        this.filter = filter;
    }

    @Override
    public void notify(Object object, byte[] data) {
        final String xml = xstream.toXML(object);
        if (data != null) {
            long length = data.length;
            log.info("Size of serialized page: " + (length / 1024) + " KB.");
        }
        xstreamLog.trace(xml);
        checkSerializedString(xml);
    }

    private void checkSerializedString(String xml) {
        List<String> matches = filter.matches(xml);
        boolean error = false;
        for (String match : matches) {
            error = true;
            log.error("Serialized class [" + match + "].");
        }
        if (error) {
            log.error("Turn on TRACE level for 'xstream' logger and check serialized xml output for domain objects.");
            throw new RuntimeException("Serialized domain objects in your wicket pages! Use AbstractEntityModels instead.");
        }
    }
}
