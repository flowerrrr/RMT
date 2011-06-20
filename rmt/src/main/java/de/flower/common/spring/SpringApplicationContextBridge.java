package de.flower.common.spring;

import de.flower.common.logging.Slf4jUtil;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper to allow return a reference to the Spring Application Context from
 * within non-Spring enabled beans. Unlike Spring MVC's WebApplicationContextUtils
 * we do not need a reference to the Servlet context for this. All we need is
 * for this bean to be initialized during application startup.
 *
 * Note: This is a general way of accessing spring beans from legacy code.
 * In a webcontext one could also use {org.springframework.web.context.support.WebApplicationContextUtils#getWebApplicationContext(javax.servlet.ServletContext)}
 *
 * @author oblume
 */
public final class SpringApplicationContextBridge implements ApplicationContextAware {

	/** The log. */
    private final static Logger log = Slf4jUtil.getLogger();

    /** The application context. */
	private ApplicationContext applicationContext;

	/** The self. */
	private static SpringApplicationContextBridge self;

	/** The application context listener list. Classes can subscribe and will be notified when the application context is set. Useful for classes that are instaniated before the application context is initialized. e.g. SpringBeanDelegateFilter. */
	private List<ApplicationContextAware> applicationContextListnerList = new ArrayList<ApplicationContextAware>();

	/**
	 * Disable instantiation.
	 */
	private SpringApplicationContextBridge() {
		log.info("Creating " + this);
	}

	/**
	 * Gets the single instance of SpringApplicationContextBridge.
	 *
	 * @return single instance of SpringApplicationContextBridge
	 */
	public static SpringApplicationContextBridge getInstance() {
        if (self == null) {
            self = new SpringApplicationContextBridge();
        }
        return self;
    }


    /**
     * Gets the application context.
     *
     * @return the application context
     */
    public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("Attribute 'applicationContext' must not be null.");
		}
        return applicationContext;
    }

    /**
     * This method is called from within the ApplicationContext once it is
     * done starting up, it will stick a reference to itself into this bean.
     *
     * @param applicationContext the application context
     *
     * @throws BeansException the beans exception
     */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.info("Setting application context=" + applicationContext);
		if(this.applicationContext != null && !this.applicationContext.equals(applicationContext)) {
			log.warn("Overriding already set application context!"); // see trac-#87
		}
		this.applicationContext = applicationContext;
		notifyListeners(applicationContext);
	}

	/**
	 * Notify listeners.
	 */
	private void notifyListeners(ApplicationContext applicationContext) {
		for (ApplicationContextAware listener : applicationContextListnerList) {
			try {
				listener.setApplicationContext(applicationContext);
			} catch (Exception e) {
				log.error("Error while notifying listner.", e);
			}
		}
	}

	/**
	 * This is about the same as context.getBean("beanName"), except it has its
	 * own handle to the Spring context, so calling this method
	 * will give access to the beans by name in the Spring application context.
	 * As in the context.getBean("beanName") call, the caller must cast to the
	 * appropriate target class. If the bean does not exist, then a Runtime error
	 * will be thrown.
	 *
	 * @param beanName the name of the bean to get.
	 *
	 * @return an Object reference to the named bean.
	 */
	public Object getBean(String beanName) {
		if (applicationContext == null) {
			throw new IllegalStateException("Attribute 'applicationContext' must not be null.");
		}
		return applicationContext.getBean(beanName);
	}

	/**
	 * Register application context listener.
	 *
	 * @param callback the callback
	 */
	public void registerApplicationContextListener(ApplicationContextAware callback) {
		applicationContextListnerList.add(callback);
	}

}
