package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.ui.app.Resource;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author flowerrrr
 */
public class DatePicker extends DateTextField {

    public DatePicker(final String id, final String datePattern) {
        super(id, null, new PatternDateConverter(datePattern, true));
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavaScriptReference(Resource.jqueryJsUrl);
        response.renderJavaScriptReference(Resource.datepickerJsUrl);
        response.renderCSSReference(Resource.datepickerCssUrl);
        AjaxRequestTarget target = AjaxRequestTarget.get();
        if (target != null) {
            // after ajax calls the datepicker must be reinitialized again.
            String id = getMarkupId();
            response.renderOnDomReadyJavaScript("$('#" + id + "').datepicker();");
        }
    }
}
