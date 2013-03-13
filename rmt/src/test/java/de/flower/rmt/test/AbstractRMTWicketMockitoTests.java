package de.flower.rmt.test;

import de.flower.common.test.wicket.AbstractWicketMockitoTests;
import de.flower.common.test.wicket.MockitoFactoryApplicationContext;
import de.flower.common.test.wicket.WicketTester;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.security.ISecurityService;
import de.flower.rmt.security.UserDetailsBean;
import de.flower.rmt.ui.app.TestRMTApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.testng.annotations.BeforeMethod;

import static org.mockito.Mockito.when;

/**
 * Base class for ui-only tests. All dependencies are mocked.
 * Inits security context with test-user.
 *
 * @author flowerrrr
 */
public abstract class AbstractRMTWicketMockitoTests extends AbstractWicketMockitoTests {

    protected TestData testData = new TestData();

    @Override
    protected WicketTester createWicketTester(final MockitoFactoryApplicationContext mockCtx) {
        WebApplication webApp = new TestRMTApplication(mockCtx);
        WicketTester wicketTester = new WicketTester(webApp);
        wicketTester.setSerializationCheck(false);
        return wicketTester;
    }

    @BeforeMethod
    public final void initSecurityService() {
        ISecurityService securityService = mockCtx.getMock(ISecurityService.class);
        User user = testData.newUser();
        when(securityService.getUser()).thenReturn(user);

        UserDetailsBean userDetails = new UserDetailsBean(user);
        when(securityService.getCurrentUser()).thenReturn(userDetails);
    }


}