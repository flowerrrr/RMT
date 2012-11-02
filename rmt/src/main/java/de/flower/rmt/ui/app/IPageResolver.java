package de.flower.rmt.ui.app;

import org.apache.wicket.Page;
import org.apache.wicket.request.component.IRequestablePage;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
public interface IPageResolver {

    Class<? extends IRequestablePage> getHomePage();

    Class<? extends Page> getPageNotFoundPage();

    Class<? extends Page> getInternalErrorPage();

    Class<? extends Page> getAccessDeniedPage();

    Class<? extends Page> getPageExpiredPage();
}
