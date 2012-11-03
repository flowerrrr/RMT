package de.flower.common.ui.behavior;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.util.Check;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;

import java.util.HashMap;
import java.util.Map;

/**
 * behavior to be bound to textarea to transform it to a ckeditor instance.
 *
 * @author flowerrrr
 */
public class CkEditorBehavior extends Behavior {

    private String jsUrl;

    private AjaxSubmitLink ajaxSubmitLink;

    private Integer height;

    public CkEditorBehavior(final String jsUrl, final AjaxSubmitLink ajaxSubmitLink, Integer height) {
        this.jsUrl = jsUrl;
        this.ajaxSubmitLink = ajaxSubmitLink;
        this.height = height;
    }

    @Override
    public void bind(final Component component) {
        Check.isTrue(component instanceof TextArea, "Behavior can only be bound to textareas.");
        component.setOutputMarkupId(true);
        component.add(AttributeModifier.append("style", "display: none;"));

        // must call some ckeditor method before submitting the form.
        // NOTE: for better visual result don't destroy editor before ajax submit. rather do it before re-rendering the form. avoids flickering of editor.
        String s = "var editor = CKEDITOR.instances." + component.getMarkupId() + "; "
                // update form fields before submitting form
                + "editor.updateElement(); "
                // destroy any existing instance before replacing with new (better: should be done after ajax-submit, but thats hard to intercept).
                // + "CKEDITOR.instances." + component.getMarkupId() + ".destroy(); "
                // + "delete CKEDITOR.instances." + component.getMarkupId() + "; "
                + "";

        ajaxSubmitLink.setPreprocessingCallDecoratorScript(s);
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        response.renderJavaScriptReference(jsUrl);
        String s = "delete CKEDITOR.instances." + component.getMarkupId() + "; "
                + "CKEDITOR.replace('" + component.getMarkupId() + "', " + getJsonOptions() + ");";
        response.renderOnDomReadyJavaScript(s);
    }

    private String getJsonOptions() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String options = gson.toJson(getOptions());
        return options;
    }

    private Map<String, Object> getOptions() {
        Map<String, Object> options = new HashMap<>();

        options.put("toolbar", "Basic");
        if (height != null) {
            options.put("height", height + "px");
        }
        return options;
    }
}
