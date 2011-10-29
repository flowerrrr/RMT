package de.flower.rmt.ui.common.form;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class InputFieldPanel extends Panel {

    final Label labelSpan;

    final TextField textField;

    public InputFieldPanel(String id) {
        super(id);

        textField = new TextField("input");
        final FormComponentLabel label = new FormComponentLabel("label", textField);
        add(label);
        labelSpan = new Label("labelSpan");
        label.add(labelSpan);
        label.add(textField);
        add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(textField)));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        String labelKey = getLabelKey();
        labelSpan.setDefaultModel(new ResourceModel(labelKey));

        // set model of form component
        textField.setModel(new PropertyModel(textField.getForm().getModel(), this.getId()));
    }

    public String getLabelKey() {
        String labelKey;
        final IMarkupFragment markup = getMarkup();
        final MarkupElement markupElement = markup.get(0);
        labelKey = ((ComponentTag) markupElement).getAttribute("labelKey");
        return labelKey;
    }
}
