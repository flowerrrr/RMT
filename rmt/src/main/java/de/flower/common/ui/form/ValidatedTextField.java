package de.flower.common.ui.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;


/**
 * @author oblume
 */
public class ValidatedTextField<T> extends Panel {

    private boolean isValidatedAndValid = false;

    public ValidatedTextField(String id) {
        super(id);
        setOutputMarkupId(true);
        // add(new InputValidationBorder<Team>("nameBorder", form, name));
        final TextField<T> field = new TextField<T>(id);
        add(field);
        field.setRequired(false);
        field.add(new AjaxFormComponentUpdatingBehavior("onblur") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (!field.getInput().isEmpty()) {
                    isValidatedAndValid = true;
                }
                target.add(ValidatedTextField.this);
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
                isValidatedAndValid = false;
                target.add(ValidatedTextField.this);
            }

        });
        add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(field)));

        // set css class of border according to validation result.
        IModel<String> model = new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (field.getFeedbackMessage() != null) {
                    return "error";
                } else if (isValidatedAndValid) {
                    return "valid";
                } else {
                    return "";
                }

            }
        };
        add(new AttributeAppender("class", model, " "));

    }


    /**
     * Need to create markup programmatically to set the id of the input tag to the fieldname so that the
     * compoundpropertymodel of the form is happy.
     * @return
     */
    @Override
    public IMarkupFragment getAssociatedMarkup() {
        return Markup.of("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><wicket:panel><input wicket:id=\"" + getId() + "\" />\n" +
                "    <span wicket:id=\"feedback\" ></span></wicket:panel>");
    }

}
