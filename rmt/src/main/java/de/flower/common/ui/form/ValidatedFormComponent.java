package de.flower.common.ui.form;

import de.flower.common.ui.Css;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

/**
 * @author flowerrrr
 */
public class ValidatedFormComponent<T> extends Panel {

    private boolean isValidatedAndValid = false;

    final FormComponent<T> c;

    public ValidatedFormComponent(final FormComponent<T> c) {
        super(c.getId()); // must be identical with wrapped component id. other wise getMarkup() will fail.
        setOutputMarkupId(true);
        this.c = c;
        add(c);
        c.add(new AjaxFormComponentUpdatingBehavior("onblur") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (!c.getInput().isEmpty()) {
                    isValidatedAndValid = true;
                }
                target.add(ValidatedFormComponent.this);
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
                if (e != null) {
                    throw e;
                }
                isValidatedAndValid = false;
                target.add(ValidatedFormComponent.this);
            }

            @Override
             protected IAjaxCallDecorator getAjaxCallDecorator() {
                 // to avoid triggering validation when input c is empty.
                 // // TODO (flowerrrr - 24.09.11) - could be extended to avoid validation when c value has not changed
                 return new AjaxCallDecorator() {
                     @Override
                     public CharSequence decorateScript(Component c, CharSequence script) {
                         return "if(jQuery.trim(this.value)=='')return false; " + script;
                     }
                 };
             }
        });

        add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(c)));

        // set css class of border according to validation result.
        IModel<String> model = new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (c.getFeedbackMessage() != null) {
                    return Css.ERROR;
                } else if (isValidatedAndValid) {
                    return Css.VALID;
                } else {
                    return "";
                }

            }
        };
        add(new AttributeAppender("class", model, " "));

    }

    @Override
    public void onDetach() {
        // reset state of component.
        isValidatedAndValid = false;
        super.onDetach();
    }

     /**
     * Need to create markup programmatically to set the id of the input tag to the fieldname so that the
     * compoundpropertymodel of the form is happy.
     * @return
     */
    @Override
    public Markup getAssociatedMarkup() {
        return Markup.of("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><wicket:panel>"
                + getMarkup().toString(true) + "\n"
                + "    <span wicket:id=\"feedback\" ></span></wicket:panel>");
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        // replace whatever tag the user has given.
        // user can still write <input wicket:id="name" /> and it will be replaced with a div tag.
        tag.setName("div");
        super.onComponentTag(tag);
    }

    /**
     * Delegate validation behavior to the form component.
     *
     * @param behaviors
     * @return
     */
    // TODO (flowerrrr - 01.07.11) - might need further tuning.
    @Override
    public Component add(Behavior... behaviors) {
        for (Behavior behavior : behaviors) {
            if (behavior instanceof IValidator) {
                c.add(behavior);
            } else {
                super.add(behavior);
            }
        }
        return this;
    }
}
