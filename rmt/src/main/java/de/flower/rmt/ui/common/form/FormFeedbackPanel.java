package de.flower.rmt.ui.common.form;

import de.flower.common.util.Check;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author flowerrrr
 */
public class FormFeedbackPanel extends BasePanel {

    public FormFeedbackPanel(final MarkupContainer parent) {

        // separate filter to determine visibility of feedback banners.
        final IFeedbackMessageFilter filter = new ContainerFeedbackMessageFilter(parent);
        add(new WebMarkupContainer("submitSuccess") {
            @Override
            public boolean isVisible() {
                Form form = Check.notNull(findParent(Form.class));
                return isShowSuccessFeedbackPanel() && form.isSubmitted() && !Session.get().getFeedbackMessages().hasMessage(filter);
            }
        });
        add(new WebMarkupContainer("hasErrors") {
            @Override
            public boolean isVisible() {
                return Session.get().getFeedbackMessages().hasMessage(filter);
            }
        });
        final FeedbackPanel feedback;
        // here we filter only for messages that are not handled by any of the subcomponents
        add(feedback = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(parent)));
        feedback.setOutputMarkupId(true);
    }

    protected boolean isShowSuccessFeedbackPanel() {
        return true;
    }
}

