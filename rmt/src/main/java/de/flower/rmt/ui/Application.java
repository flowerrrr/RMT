package de.flower.rmt.ui;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component(value = "wicketApplication")
public class Application extends WebApplication {

    private static final String DEFAULT_ENCODING = "UTF-8";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

/*
        getMarkupSettings().setDefaultMarkupEncoding(DEFAULT_ENCODING);
        getRequestCycleSettings().setResponseRequestEncoding(DEFAULT_ENCODING);

        if (getConfigurationType().equals(WebApplication.DEPLOYMENT)) {
            getMarkupSettings().setStripWicketTags(true);
            getMarkupSettings().setStripComments(true);
            getMarkupSettings().setCompressWhitespace(true);
        }
*/

    }


    @Override
    public Class getHomePage() {
        return ScalaHomePage.class;
    }


}