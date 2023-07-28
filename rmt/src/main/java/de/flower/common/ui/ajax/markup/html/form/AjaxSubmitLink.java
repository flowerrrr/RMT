package de.flower.common.ui.ajax.markup.html.form;

import de.flower.common.ui.ajax.PreventDoubleClickAjaxCallDecorator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPreprocessingCallDecorator;
import org.apache.wicket.markup.html.form.Form;


public abstract class AjaxSubmitLink extends org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink {

    private String preprocessingCallDecoratorScript;

    public AjaxSubmitLink(String id) {
        super(id);
    }

    public AjaxSubmitLink(String id, Form<?> form) {
        super(id, form);
    }

    @Override
    protected IAjaxCallDecorator getAjaxCallDecorator() {
        IAjaxCallDecorator xdefault = new PreventDoubleClickAjaxCallDecorator();
        if (preprocessingCallDecoratorScript != null) {
            return new AjaxPreprocessingCallDecorator(xdefault) {
                @Override
                public CharSequence preDecorateScript(final CharSequence script) {
                    return preprocessingCallDecoratorScript + script;
                }
            };
        } else {
            return xdefault;
        }
    }

    @Override
    protected final void onError(AjaxRequestTarget target, Form<?> form) {
        target.add(form);
    }

    public void setPreprocessingCallDecoratorScript(final String script) {
        this.preprocessingCallDecoratorScript = script;
    }
}
