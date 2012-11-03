package de.flower.common.test.wicket;

import de.flower.common.util.ReflectionUtil;
import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.spring.ISpringContextLocator;
import org.apache.wicket.spring.injection.annot.AnnotProxyFieldValueFactory;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Resets all previously injected beans.
 * Simulates DirtiesContext.AFTER_METHOD.
 * Required cause Mockito.reset() does not fully reset mocks.
 *
 * @author flowerrrr
 */
public class SpringComponentInjectorUtils {

    public static void resetAllBeans(Object object, final SpringComponentInjector injector) {
        final IFieldValueFactory fieldValueFactory = new AnnotProxyFieldValueFactory(new ContextLocator(injector), false);
        Field[] fields = findFields(object.getClass(), fieldValueFactory);
        for (final Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                if (field.get(object) != null) {
                    field.set(object, null);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Copied from org.apache.wicket.injection.Injector#findFields(java.lang.Class<?>, org.apache.wicket.injection.IFieldValueFactory).
     * @param clazz
     * @param factory
     * @return
     */
    private static Field[] findFields(Class<?> clazz, final IFieldValueFactory factory)
   	{
   		List<Field> matched = new ArrayList<Field>();

   		while (clazz != null)
   		{
   			Field[] fields = clazz.getDeclaredFields();
   			for (final Field field : fields)
   			{
   				if (factory.supportsField(field))
   				{
   					matched.add(field);
   				}
   			}
   			clazz = clazz.getSuperclass();
   		}

   		return matched.toArray(new Field[matched.size()]);
   	}

    /**
     * Copied from org.apache.wicket.spring.injection.annot.SpringComponentInjector.ContextLocator.
     */
    private static class ContextLocator implements ISpringContextLocator {

        private transient ApplicationContext context;

        private static final long serialVersionUID = 1L;

        private SpringComponentInjector injector;

        public ContextLocator(final SpringComponentInjector injector) {
            this.injector = injector;
        }

        public ApplicationContext getSpringContext() {
            if (context == null) {
                MetaDataKey<ApplicationContext> contextKey = (MetaDataKey<ApplicationContext>) ReflectionUtil.getField(injector, "CONTEXT_KEY");
                context = Application.get().getMetaData(contextKey);
            }
            return context;
        }
    }
}
