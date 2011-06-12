package de.flower.common.ui.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author oblume
 */
public class InputValidationBorder<T> extends Border {

	protected FeedbackPanel feedback;

	public InputValidationBorder(String id, final Form<T> form, final FormComponent<? extends Object> inputComponent) {
		super(id);
		add(inputComponent);
		inputComponent.setRequired(false);
		inputComponent.add(new AjaxFormComponentUpdatingBehavior("onblur") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e) {
				target.add(feedback);
			}

		});

		// inputComponent.add(new Jsr303PropertyValidator(form.getModelObject().getClass(), inputComponent.getId()));

		add(feedback = new FeedbackPanel("inputErrors", new ContainerFeedbackMessageFilter(this)));
		feedback.setOutputMarkupId(true);
	}

}