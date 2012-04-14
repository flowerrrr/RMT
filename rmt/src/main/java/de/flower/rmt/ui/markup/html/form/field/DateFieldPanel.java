package de.flower.rmt.ui.markup.html.form.field;

import de.flower.rmt.ui.app.Resource;
import de.flower.rmt.util.Dates;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author flowerrrr
 */
public class DateFieldPanel extends AbstractFormFieldPanel {

    public DateFieldPanel(String id) {
        super(id, DateTextField.forDatePattern(ID, Dates.DATE_MEDIUM));
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavaScriptReference(Resource.jqueryJsUrl);
        response.renderJavaScriptReference(Resource.datepickerJsUrl);
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
