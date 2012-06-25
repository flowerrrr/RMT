package de.flower.common.ui.ajax.markup.html;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.model.IModel;

/**
 * Overrides some default behavior.
 * @author flowerrrr
 */
public class AjaxEditableMultiLineLabelExtended extends AjaxEditableMultiLineLabel {

    public AjaxEditableMultiLineLabelExtended(final String id, final IModel iModel) {
        super(id, iModel);
    }

    @Override
    public void onEdit(final AjaxRequestTarget target) {
        super.onEdit(target);
        // by default the content is selected. leads to problems when accidently hitting a key and thus clearing out
        // the contents. no many will know that hitting ESC can restore the original value.
        String deselectAndPutCursorAtEnd = "(function() { var temp;\n" +
                "    var id = '#" + getEditor().getMarkupId() + "';\n" +
                "    temp=$(id).val();\n" +
                "    $(id).val('');\n" +
                "    $(id).val(temp);\n" +
                "    $(id).focus();\n" +
                "})();";
        target.appendJavaScript(deselectAndPutCursorAtEnd);
    }
}