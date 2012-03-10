package de.flower.rmt.ui.manager.page.squad;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.js.JQuery;
import de.flower.rmt.model.Team;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class SquadSecondaryPanel extends BasePanel {

    private AjaxSlideTogglePanel addPlayerPanel;

    public SquadSecondaryPanel(IModel<Team> model) {

        final AjaxLink addButton = new AjaxLink("addButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show inline  dialog with squad edit form.
                addPlayerPanel.show(target);
            }

//            @Override
//            protected IAjaxCallDecorator getAjaxCallDecorator() {
//                return new AjaxCallDecorator() {
//                    @Override
//                    public CharSequence decorateScript(final Component c, final CharSequence script) {
//                        String fadeOutJs = JQuery.fadeOut(c, "slow");
//                        return fadeOutJs + script;
//                    }
//                };
//            }
        };
        add(addButton);

        addPlayerPanel = new AjaxSlideTogglePanel("addPlayerPanel", new AddPlayerPanel(model)) {
            @Override
            public void onHide(AjaxRequestTarget target) {
                target.prependJavaScript(JQuery.fadeIn(addButton, "slow"));
            }
        };
        add(addPlayerPanel);
    }
}
