package de.flower.rmt.ui.common.form.field;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author flowerrrr
 */
public class DateFieldPanel extends FormFieldPanel {

    public DateFieldPanel(String id) {
        super(id, DateTextField.forDatePattern(ID, "dd.MM.yyyy"));
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        AjaxRequestTarget target = AjaxRequestTarget.get();
        if (target != null) {
            // after ajax calls the datepicker must be reinitialized again.
            String id = getFormComponent().getMarkupId();
            response.renderOnDomReadyJavaScript("$('#" + id + "').datepicker();");
        }
    }

    /**
     * Since user can only input correct dates no need for instant validation.
     * Further constraints like @Past or @Future will be validated when
     * full form is submitted.
     *
     * Instant validation would also collide with datepicker.
     *
     * @return
     */
    @Override
    protected boolean isInstantValidationEnabled() {
        return false;
    }


}
