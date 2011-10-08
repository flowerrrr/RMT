package de.flower.common.ui.form;

import de.flower.common.model.IEntity;
import de.flower.common.util.Check;
import org.apache.wicket.Session;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.jsr303.PropertyValidation;

/**
 * Form for editing domain objects.
 * Assumes:
 * - model containing domain object is a LoadableDetachableModel. Needed to avoid
 * serializing domain objects to the page store.
 * @author flowerrrr
 */
public class EntityForm<T extends IEntity> extends Form<T> {

    public EntityForm(String id, IModel<T> model) {
        super(id, new CompoundPropertyModel<T>(model));
        Check.isInstanceOf(LoadableDetachableModel.class, model);

        add(new WebMarkupContainer("hasErrors") {
            @Override
            public boolean isVisible() {
                return !Session.get().getFeedbackMessages().isEmpty();
            }
        });
        final FeedbackPanel feedback;
        add(feedback = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this)));
        feedback.setOutputMarkupId(true);
        add(new PropertyValidation());
    }

    public void replaceModel(IModel<T> model) {
        Check.isInstanceOf(LoadableDetachableModel.class, model);
        setModel(new CompoundPropertyModel<T>(model));
    }

}
