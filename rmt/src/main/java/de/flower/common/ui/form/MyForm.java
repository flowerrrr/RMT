package de.flower.common.ui.form;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.jsr303.PropertyValidation;

/**
 * @author oblume
 */
public class MyForm<T> extends Form<T> {

    public MyForm(String id, T entity) {
        super(id, new CompoundPropertyModel<T>(entity));
        final FeedbackPanel feedback;
        add(feedback = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this)));
        feedback.setOutputMarkupId(true);
        add(new PropertyValidation(this, true));
    }

}
