package de.flower.rmt.ui.page.error;


import de.flower.rmt.test.AbstractRMTIntegrationTests
import org.apache.wicket.mock.MockWebRequest
import org.apache.wicket.protocol.http.mock.MockHttpServletRequest
import org.apache.wicket.request.IRequestHandler
import org.apache.wicket.request.Request
import org.apache.wicket.request.RequestHandlerStack
import org.apache.wicket.request.RequestHandlerStack.ReplaceHandlerException
import org.apache.wicket.request.http.handler.RedirectRequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.testng.Assert
import org.testng.annotations.Test

import static org.testng.Assert.assertEquals

/**
 * @author flowerrrr
 */
@groovy.transform.TypeChecked
class PageNotFoundAutoRedirecterTest extends AbstractRMTIntegrationTests {

    @Autowired
    private PageNotFoundAutoRedirecter pageNotFoundAutoRedirecter;

    @Test
    def void testCheckAutoRedirect() {
        Request request = new MockWebRequest(null) {

            @Override
            Object getContainerRequest() {
                MockHttpServletRequest request = new MockHttpServletRequest(null, null, null)
                request.setAttribute("javax.servlet.forward.request_uri", "foo");
              	request.setAttribute("javax.servlet.forward.servlet_path", "/events");
              	request.setAttribute("javax.servlet.forward.context_path", "for");
              	request.setAttribute("javax.servlet.forward.query_string", "foo");
                return request;
            }
        }
        try {
            pageNotFoundAutoRedirecter.checkAutoRedirect(request)
            Assert.fail("Expected exception was not thrown")
        } catch (ReplaceHandlerException e) {
            RequestHandlerStack rhs = new RequestHandlerStack() {

                @Override
                protected void respond(final IRequestHandler handler) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                protected void detach(final IRequestHandler handler) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            }
            RedirectRequestHandler handler = rhs.resolveHandler(e) as RedirectRequestHandler
            assertEquals(handler.getRedirectUrl(), "/events")
        }

    }

}
