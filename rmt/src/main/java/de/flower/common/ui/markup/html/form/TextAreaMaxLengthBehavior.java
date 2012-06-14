package de.flower.common.ui.markup.html.form;

import de.flower.common.util.Check;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;

/**
 * @author flowerrrr
 */
public class TextAreaMaxLengthBehavior extends Behavior {

    private int maxLength;

    private final static String ID = TextAreaMaxLengthBehavior.class.getName();

    public TextAreaMaxLengthBehavior(final int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void bind(final Component component) {
        Check.isTrue(component instanceof TextArea, "Behavior can only be bound to textareas.");
        component.add(AttributeModifier.append("maxlength", maxLength));
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        // Get all textareas that have a \"maxlength\" property. Now, and when later adding HTML using jQuery-scripting
        String js = "    $('textarea[maxlength]').live('keyup blur', function() {\n" +
                // Store the maxlength and value of the field.
                "        var maxlength = $(this).attr('maxlength');\n" +
                "        var val = $(this).val();\n" +
                "\n" +
                // Trim the field if it has content over the maxlength.
                "        if (val.length > maxlength) {\n" +
                "            $(this).val(val.slice(0, maxlength));\n" +
                "        }\n" +
                "    });";
        response.renderJavaScript(js, ID);
    }
}
