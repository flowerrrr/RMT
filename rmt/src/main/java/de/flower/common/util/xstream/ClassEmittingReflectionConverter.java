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

    public ClassEmittingReflectionConverter(XStream xstream) {
        super(xstream.getMapper(), new JVM().bestReflectionProvider());
    }

    @Override
    public void marshal(Object original, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        if (log.isTraceEnabled()) {
            log.trace("marshal([" + original + "]");
        }
        writer.addAttribute("type", original.getClass().getName());
        try {
            super.marshal(original, writer, context);
        } catch (RuntimeException e) {
            if (original instanceof SingularAttributeImpl) {
                SingularAttributeImpl attr = (SingularAttributeImpl) original;
                log.error(attr.getName());
            }
            throw e;
        }
    }
}
