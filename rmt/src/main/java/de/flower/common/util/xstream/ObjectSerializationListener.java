package de.flower.common.util.xstream;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import de.flower.common.util.Clazz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Try threadlocal.
 */
public class ObjectSerializationListener {

    private final static Logger log = LoggerFactory.getLogger(ObjectSerializationListener.class);

    public static class Context {

        public Multiset<Class<?>> typeSet = TreeMultiset.create(new Comparator<Class<?>>() {
            @Override
            public int compare(final Class<?> o1, final Class<?> o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        public String toString() {
            String s = "";
            for (Multiset.Entry<Class<?>> entry : typeSet.entrySet()) {
                s += entry.getElement().getName() + " = " + entry.getCount() + "\n";
            }
            return s;
        }
    }

    private static ThreadLocal<Context> threadLocal = new ThreadLocal<Context>();

    public void notify(final Object object) {
        Class<?> clazz = object.getClass();
        if (Clazz.isAnonymousClass(clazz)) {
            clazz = Clazz.getSuperClass(clazz);
        }
        Context context = getContext();
        context.typeSet.add(clazz);
    }

    public static void reset() {
        threadLocal.set(new Context());
    }

    public static Context getContext() {
        Context context = threadLocal.get();
        if (context == null) {
            throw new IllegalStateException("Must call #reset first.");
        }
        return context;
    }

}
