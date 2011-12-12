package de.flower.rmt.test;

import de.flower.common.test.wicket.AbstractWicketMockitoTests;
import de.flower.common.test.wicket.WicketTester;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.type.Password;
import de.flower.rmt.ui.app.TestApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author flowerrrr
 */

public abstract class AbstractRMTWicketMokitoTests extends AbstractWicketMockitoTests {


    @Override
    protected WicketTester createWicketTester(final ApplicationContext mockCtx) {
        WebApplication webApp = new TestApplication(mockCtx);
        WicketTester wicketTester = new WicketTester(webApp);
        wicketTester.getLoggingSerializerFilter().addInclusion("\"de\\.flower\\.rmt\\.model\\.[^-]*?\"");
        wicketTester.getLoggingSerializerFilter().addExclusion(RSVPStatus.class.getName());
        wicketTester.getLoggingSerializerFilter().addExclusion(Password.class.getName());
        return wicketTester;
    }
}