package de.flower.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.pageStore.memory.DataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.lang.WicketObjects;
import org.apache.wicket.util.time.Duration;

/**
 * A Wicket initializer that configures the application that way so it is possible to run it in
 * Google AppEngine
 */
public class GaeInitializer implements IInitializer {

    /**
     * GAE hack to enable resource reloading of html-templates.
     */
    private StaticModificationWatcher staticModificationWatcher;


    public void init(Application application) {

        // disable ModificationWatcher, cause it spawns a thread
        application.getResourceSettings().setResourcePollFrequency(null);

        initResourceReloading(application);

        // use plain JDK Object(Input|Output)Stream
        WicketObjects.setObjectStreamFactory(new GaeObjectStreamFactory());

        // save older version of pages in the HttpSession
        final DataStoreEvictionStrategy evictionStrategy;
        if (application instanceof GaeApplication) {
            evictionStrategy = ((GaeApplication) application).getEvictionStrategy();
        } else {
            evictionStrategy = new PageNumberEvictionStrategy(10);
        }

        application.setPageManagerProvider(new GaePageManagerProvider(application, evictionStrategy));

        // disable file cleaning because it starts a new thread
        application.getResourceSettings().setFileUploadCleaner(null);
    }

    /**
     * Since the ModificationWatcher is disabled the html-template reloading does not work.
     * So we intercept every request and check if a resource has to be reloaded.
     *
     * @param application
     */
    private void initResourceReloading(Application application) {
        if (application.getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
            staticModificationWatcher = new StaticModificationWatcher();
            application.getResourceSettings().setResourceWatcher(staticModificationWatcher);
            application.getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
        }

        // intercept request cylce to scan for new version of resources.
        application.getRequestCycleListeners().add(new AbstractRequestCycleListener() {
            @Override
            public void onBeginRequest(RequestCycle cycle) {
                if (staticModificationWatcher != null) {
                    staticModificationWatcher.runCheck();
                }
            }
        });
    }


    public void destroy(Application application) {
    }
}
