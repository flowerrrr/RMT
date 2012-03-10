package de.flower.common.ui.ajax.markup.html;

import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPreprocessingCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.JavaScriptUtils;

/**
 * {@code AjaxLink} with a JavaScript confirmation message before the AJAX submit.
 *
 * @param <T> type of model object
 */
public abstract class AjaxLinkWithConfirmation<T> extends AjaxLink<T> {

    private final IModel<String> confirmationQuestion;

    public AjaxLinkWithConfirmation(final String id, final IModel<String> confirmationQuestion) {
        super(id);
        this.confirmationQuestion = confirmationQuestion;
    }

    @Override
    protected final IAjaxCallDecorator getAjaxCallDecorator() {
        return new AjaxPreprocessingCallDecorator(super.getAjaxCallDecorator()) {
            @Override
            public CharSequence preDecorateScript(final CharSequence script) {
                return decorateScriptWithConfirmationMessage(script, confirmationQuestion.getObject());
            }
        };
    }

    /**
     * Decorate script with confirmation message.
     *
     * @param script the script
     * @param confirmationMessage the confirmation message
     * @return the string
     */
    public static final String decorateScriptWithConfirmationMessage(final CharSequence script,
            final String confirmationMessage) {
        final String myScript = "var r=confirm('" + JavaScriptUtils.escapeQuotes(confirmationMessage)
                + "'); if (r!=true) { return false; } " + script;
        return myScript;
    }

}
