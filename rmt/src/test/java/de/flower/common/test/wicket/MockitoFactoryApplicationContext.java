package de.flower.common.test.wicket;

import de.flower.common.util.Check;
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
 *
 * @author flowerrrr
 */
public class MockitoFactoryApplicationContext extends ApplicationContextMock {

    private final static Logger log = LoggerFactory.getLogger(MockitoFactoryApplicationContext.class);

    private boolean verboseLogging;

    protected void createAndAddMock(final Class type) {
        Check.notNull(type);
        Object bean;
        if (verboseLogging) {
            bean = mock(type, withSettings().verboseLogging());
        } else {
            bean = mock(type);
        }
        String name = type.getSimpleName();
        log.info("Adding new mock [" + name + ", " + type.getName() + "] to mock context.");
        putBean(name, bean);
    }

    @Override
    public String[] getBeanNamesForType(final Class type) {
        String[] names = super.getBeanNamesForType(type);
        if (names.length == 0) {
            createAndAddMock(type);
            // now call will return our bean that we've just created.
            names = super.getBeanNamesForType(type);
        }
        return names;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(final Class<T> type) throws BeansException {
        Map<String, T> map = super.getBeansOfType(type);
        if (map.isEmpty()) {
            createAndAddMock(type);
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
}
