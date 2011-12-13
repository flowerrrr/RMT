package de.flower.rmt.ui.common.form.field;

import de.flower.common.ui.Css;
import de.flower.common.util.Check;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.validation.IValidator;
import org.wicketstuff.jsr303.PropertyValidator;

/**
 * @author flowerrrr
 */
public class FormFieldPanel extends Panel {

    // must match the wicket:for attribute of the label
    public final static String ID = "input";

    public static final String LABEL_KEY = "labelKey";

    private boolean isValidated = false;

    private final FormComponent formComponent;

    public FormFieldPanel(String id, FormComponent fc) {
        super(id);
        setOutputMarkupId(true);

        formComponent = fc;
        add(formComponent);

        // set css class of border according to validation result.
        IModel<String> cssClassModel = new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (formComponent.getFeedbackMessage() != null) {
                    return Css.ERROR;
                } else if (isValidated) {
                    return Css.VALID;
                } else {
                    return "";
                }
            }
        };

        add(new AttributeAppender("class", cssClassModel, " "));

        add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(formComponent)));

        // use form's compoundpropertymodel by default but let component override it if desired.
        if (formComponent.getModel() == null) {
            // set model of form component. form.getForm not available at constructor time, so we have
            // to use wrapping model to defer lookup of form.
            IModel formModelWrapperModel = new AbstractReadOnlyModel() {

                @Override
                public Object getObject() {
                    return formComponent.getForm().getModel();
                }
            };
            formComponent.setModel(new PropertyModel(formModelWrapperModel, this.getId()));
        }

        formComponent.setLabel(new LoadableDetachableModel<String>() {
            /**
             * Have to delay lookup of resourceKey until component is rendered.
             */
            @Override
            protected String load() {
                String resourceKey = Check.notNull(getLabelKey());
                return new ResourceModel(resourceKey).getObject();
            }
        });

         // add validation
        if (isInstantValidationEnabled()) {
            formComponent.add(new PropertyValidator(formComponent));
            formComponent.add(new AjaxFormComponentUpdatingBehavior("onchange") {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    isValidated = true;
                    target.add(FormFieldPanel.this);
                }

                @Override
                protected void onError(AjaxRequestTarget target, RuntimeException e) {
                    if (e != null) {
                        throw e;
                    }
                    isValidated = true;
                    target.add(FormFieldPanel.this);
                }
            });
        }
    }

    @Override
    public void onDetach() {
        // reset state of component.
        isValidated = false;
        super.onDetach();
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        // replace whatever tag the user has given.
        // user can still write <input wicket:id="name" /> and it will be replaced with a div tag.
        tag.setName("div");
        // need to set twitter-bootstrap class 'clearfix'
        tag.put("class", "clearfix");
        tag.remove(LABEL_KEY);
        tag.setNamespace(null);
        super.onComponentTag(tag);
    }

    public String getLabelKey() {
        String labelKey;
        final IMarkupFragment markup = getMarkup();
        final MarkupElement markupElement = markup.get(0);
        labelKey = ((ComponentTag) markupElement).getAttribute(LABEL_KEY);
        return labelKey;
    }

    public FormComponent getFormComponent() {
        return formComponent;
    }

    public void addValidator(final IValidator<?> validator) {
        formComponent.add(validator);
    }

    /**
     * Override this method to disable standard validation behavior.
     * @return
     */
    protected boolean isInstantValidationEnabled() {
        return true;
    }

 }
