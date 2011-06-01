package de.flower.rmt.ui.manager;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;

/**
 * @author oblume
 */
@AuthorizeInstantiation("ROLE_MANAGER")
public class ManagerBasePage extends WebPage {
}
