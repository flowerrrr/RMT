package de.flower.rmt.ui.common.app;

import de.flower.rmt.ui.app.RMTApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;

/**
 * @author oblume
 */
public class TestApplication extends RMTApplication {

    private ApplicationContext ctx;

    public TestApplication(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    protected SpringComponentInjector getSpringComponentInjector() {
        return new SpringComponentInjector(this, ctx);
    }



}
