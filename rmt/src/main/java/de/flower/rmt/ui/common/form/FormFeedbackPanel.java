package de.flower.rmt.ui.common.form;

import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.Session;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author flowerrrr
 */
public class FormFeedbackPanel extends BasePanel {

    public FormFeedbackPanel(final IFeedbackMessageFilter filter) {
        add(new WebMarkupContainer("hasErrors") {
            @Override
            public boolean isVisible() {
                return !Session.get().getFeedbackMessages().isEmpty();
            }
        });
        final FeedbackPanel feedback;
        add(feedback = new FeedbackPanel("feedback", filter));
        feedback.setOutputMarkupId(true);
     }


}

