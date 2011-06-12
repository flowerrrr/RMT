package de.flower.rmt.ui.player;

import de.flower.rmt.ui.app.AbstractBasePage;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * @author oblume
 */
@AuthorizeInstantiation("ROLE_USER")
public class PlayerBasePage extends AbstractBasePage {
}
