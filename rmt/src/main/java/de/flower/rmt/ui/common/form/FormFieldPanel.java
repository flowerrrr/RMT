package de.flower.rmt.ui.common.form;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.IValidator;

/**
 * @author flowerrrr
 */
public class FormFieldPanel extends Panel {

    final FormComponent formComponent;

    public FormFieldPanel(String id, FormComponent fc) {
        super(id);
        // do not render calling markup
        setRenderBodyOnly(true);

        formComponent = fc;
        // set model of form component
        formComponent.setModel(new PropertyModel(formComponent.getForm().getModel(), this.getId()));
        add(formComponent);
        add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(formComponent)));
    }

    /**
     * Do some initialization stuff that cannot be done inside constructor call.
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();

        // retrieving the 'calling' markup cannot be done in constructor.
        String labelKey = getLabelKey();
        formComponent.setLabel(new ResourceModel(labelKey));

    }

    public String getLabelKey() {
        String labelKey;
        final IMarkupFragment markup = getMarkup();
        final MarkupElement markupElement = markup.get(0);
        labelKey = ((ComponentTag) markupElement).getAttribute("labelKey");
        return labelKey;
    }

    public FormComponent getFormComponent() {
        return formComponent;
    }

    public void addValidator(final IValidator<?> validator) {
        formComponent.add(validator);
    }


}
