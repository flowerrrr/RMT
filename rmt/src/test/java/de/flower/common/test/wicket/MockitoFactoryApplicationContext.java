package de.flower.common.test.wicket;

import de.flower.common.util.Check;
import de.flower.common.util.ReflectionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/**
 * Extends wickets ApplicationContextMock by automatically creating mockito mocks whenever
 * a bean is requested.
 * Saves developer from adding all mocks to mock context in test-setup.
 * <p/>
 * Based on the ideas of http://www.petrikainulainen.net/programming/tips-and-tricks/mocking-spring-beans-with-apache-wicket-and-mockito/.
 */
public class MockitoFactoryApplicationContext extends ApplicationContextMock {

    private final static Logger log = LoggerFactory.getLogger(MockitoFactoryApplicationContext.class);

    private boolean verboseLogging;

    protected void createAndAddMock(String name, final Class type) {
        Check.notNull(type);
        Object bean;
        if (verboseLogging) {
            bean = mock(type, withSettings().verboseLogging());
        } else {
            bean = mock(type);
        }
        if (name == null) {
            name = StringUtils.uncapitalize(type.getSimpleName());
        }
        log.info("Adding new mock [" + name + ", " + type.getName() + "] to mock context.");
        putBean(name, bean);
    }

    @Override
    public <T> T getBean(final String name, final Class<T> requiredType) throws BeansException {
        T bean;
        try {
            bean = super.getBean(name, requiredType);
        } catch (BeansException e) {
            createAndAddMock(name, requiredType);
            bean = super.getBean(name, requiredType);
        }
        return bean;
    }

    @Override
    public String[] getBeanNamesForType(final Class type) {
        String[] names = super.getBeanNamesForType(type);
        if (names.length == 0) {
            createAndAddMock(null, type);
            // now call will return our bean that we've just created.
            names = super.getBeanNamesForType(type);
        }
        return names;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(final Class<T> type) throws BeansException {
        Map<String, T> map = super.getBeansOfType(type);
        if (map.isEmpty()) {
            createAndAddMock(null, type);
            map = super.getBeansOfType(type);
        }
        return map;
    }

    /**
     * Required for ApplicationContextAwareValidationFactory.
     *
     * @return
     * @throws IllegalStateException
     */
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return new DefaultListableBeanFactory();
    }

    /**
     * Return mock for given type. If mock does not exist it will be created.
     * Use this method to get access to the mocks if changing their behavior is needed.
     *
     * @param type
     * @param <T>
     * @return
     */
    public <T> T getMock(Class<T> type) {
        return getBean(type);
    }

    public void setVerboseLogging(final boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    public Map<String, Object> getBeans() {
        return (Map<String, Object>) ReflectionUtil.getField(this, "beans");
    }
}
