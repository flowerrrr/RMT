package de.flower.wicket.protocol.http;

import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.IPageManager;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.page.PersistentPageManager;
import org.apache.wicket.pageStore.DefaultPageStore;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.io.IObjectStreamFactory;
import org.apache.wicket.util.lang.WicketObjects;

import java.io.*;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 */
public abstract class GaeWicketApplication extends WebApplication {
    /**
     * Constructor
     */
    public GaeWicketApplication() {
    }


    @Override
    protected void init() {
        super.init();

        getResourceSettings().setResourcePollFrequency(null);

        WicketObjects.setObjectStreamFactory(new IObjectStreamFactory() {

            @Override
            public ObjectInputStream newObjectInputStream(InputStream in)
                    throws IOException {
                return new ObjectInputStream(in);
            }

            @Override
            public ObjectOutputStream newObjectOutputStream(OutputStream out)
                    throws IOException {
                return new ObjectOutputStream(out);
            }

        });

        setPageManagerProvider(new DefaultPageManagerProvider(this) {

            public IPageManager get(IPageManagerContext pageManagerContext) {
                IDataStore dataStore = new HttpSessionDataStore(pageManagerContext, new PageNumberEvictionStrategy(10));
                IPageStore pageStore = new DefaultPageStore(getName(), dataStore,
                        getCacheSize());
                return new PersistentPageManager(getName(), pageStore, pageManagerContext);

            }
        });
    }

}