package de.flower.rmt.ui.common.form;

import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.Session;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author flowerrrr
 */
public class FormFeedbackPanel extends BasePanel {

    public FormFeedbackPanel() {
        add(new WebMarkupContainer("hasErrors") {
            @Override
            public boolean isVisible() {
                return !Session.get().getFeedbackMessages().isEmpty();
            }
        });
        final FeedbackPanel feedback;
        add(feedback = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this)));
        feedback.setOutputMarkupId(true);

    }
}

