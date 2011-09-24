package de.flower.common.ui.form;

import org.apache.wicket.Session;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.jsr303.PropertyValidation;

/**
 * @author flowerrrr
 */
public class MyForm<T> extends Form<T> {

    public MyForm(String id, T entity) {
        super(id, new CompoundPropertyModel<T>(entity));
        add(new WebMarkupContainer("hasErrors") {
            @Override
            public boolean isVisible() {
                return !Session.get().getFeedbackMessages().isEmpty();
            }
        });
        final FeedbackPanel feedback;
        add(feedback = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this)));
        feedback.setOutputMarkupId(true);
        add(new PropertyValidation(this, true));
    }

}
