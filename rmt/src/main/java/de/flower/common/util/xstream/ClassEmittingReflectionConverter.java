package de.flower.common.util.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.hibernate.ejb.metamodel.SingularAttributeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class ClassEmittingReflectionConverter extends ReflectionConverter {

    private final static Logger log = LoggerFactory.getLogger(ClassEmittingReflectionConverter.class);

    private ObjectSerializationListener listener;

    public ClassEmittingReflectionConverter(XStream xstream) {
        super(xstream.getMapper(), new JVM().bestReflectionProvider());
    }

    @Override
    public void marshal(Object original, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        if (log.isTraceEnabled()) {
            log.trace("marshal([" + original + "]");
        }
        writer.addAttribute("type", original.getClass().getName());
        if (listener != null) {
            listener.notify(original);
        }
        try {
            super.marshal(original, writer, context);
        } catch (RuntimeException e) {
            // what exactly was this for?
            if (original instanceof SingularAttributeImpl) {
                SingularAttributeImpl attr = (SingularAttributeImpl) original;
                log.error(attr.getName());
            }
            throw e;
        }
    }

    public ObjectSerializationListener getListener() {
        return listener;
    }

    public void setListener(final ObjectSerializationListener listener) {
        this.listener = listener;
    }
}
