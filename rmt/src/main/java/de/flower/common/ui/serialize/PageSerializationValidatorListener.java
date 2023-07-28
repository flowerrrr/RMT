package de.flower.common.ui.serialize;

import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.thoughtworks.xstream.XStream;
import de.flower.common.util.xstream.ClassEmittingReflectionConverter;
import de.flower.common.util.xstream.ObjectSerializationListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Monitors serialized pages and checks for serialized entities.
 *
 * @author flowerrrr
 */
@Component
public class PageSerializationValidatorListener {

    public static class PageSerializationException extends RuntimeException {

        public PageSerializationException(final String message) {
            super(message);
        }
    }

    private final static Logger log = LoggerFactory.getLogger(PageSerializationValidatorListener.class);

    private final static Logger xstreamLog = LoggerFactory.getLogger("xstream");

    private XStream xstream;

    private Filter filter;

    @Autowired
    public PageSerializationValidatorListener(Filter filter) {
        xstream = new XStream();
        // by default xstream does not output the classname of serialized fields.
        ClassEmittingReflectionConverter converter = new ClassEmittingReflectionConverter(xstream);
        final ObjectSerializationListener objectSerializationListener = new ObjectSerializationListener();
        converter.setListener(objectSerializationListener);
        xstream.registerConverter(converter, XStream.PRIORITY_VERY_LOW);

        this.filter = filter;
    }

    public void notify(Object object, byte[] data) {
        ObjectSerializationListener.reset();
        final String xml = xstream.toXML(object);
        if (data != null) {
            long length = data.length;
            log.info("Size of serialized page: " + (length / 1024) + " KB.");
        }
        xstreamLog.trace(xml);
        ObjectSerializationListener.Context context = ObjectSerializationListener.getContext();
        checkSerializedObjects(context);
        ObjectSerializationListener.reset();
    }

    /**
     * <pre>
     * class on whitelist -> ok
     * class on blacklist -> exception
     * otherwise -> log.warn
     * </pre>
     */
    private void checkSerializedObjects(ObjectSerializationListener.Context context) {
        List<String> blackListed = Lists.newArrayList();
        List<String> undefinedList = Lists.newArrayList();
        for (Multiset.Entry<Class<?>> entry : context.typeSet.entrySet()) {
            String className = entry.getElement().getName();
            switch (filter.matches(className)) {
                case WHITELIST:
                    break;
                case BLACKLIST:
                    blackListed.add(className);
                    break;
                default:
                    undefinedList.add(className);
            }
        }

        if (!undefinedList.isEmpty()) {
            log.warn("Unknown classes. Consider adding them to white/blacklist:\n{}", StringUtils.join(undefinedList, "\n"));
        }
        if (!blackListed.isEmpty()) {
            log.error("Blacklisted serialized classes in page: {}", blackListed);
            log.error("Turn on TRACE level for 'xstream' logger and check serialized xml output for violating objects.");
            throw new PageSerializationException("Serialized domain objects in your wicket pages! Use AbstractEntityModels instead.");
        }
    }

    public void setFilter(final Filter filter) {
        this.filter = filter;
    }
}
