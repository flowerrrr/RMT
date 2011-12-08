package de.flower.rmt.ui.common.form;

import de.flower.common.util.Check;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.Session;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author flowerrrr
 */
public class FormFeedbackPanel extends BasePanel {

    public FormFeedbackPanel(final IFeedbackMessageFilter filter) {
        add(new WebMarkupContainer("submitSuccess") {
            @Override
            public boolean isVisible() {
                Form form = Check.notNull(findParent(Form.class));
                return form.isSubmitted() && Session.get().getFeedbackMessages().isEmpty();
            }
        });
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

