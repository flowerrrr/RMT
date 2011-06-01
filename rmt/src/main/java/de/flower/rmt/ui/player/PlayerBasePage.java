package de.flower.rmt.ui.player;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;

/**
 * @author oblume
 */
@AuthorizeInstantiation("ROLE_USER")
public class PlayerBasePage extends WebPage {
}
