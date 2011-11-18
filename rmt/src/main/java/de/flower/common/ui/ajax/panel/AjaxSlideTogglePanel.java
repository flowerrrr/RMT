package de.flower.common.ui.ajax.panel;

import de.flower.common.ui.js.JQuery;
import de.flower.common.util.Check;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author flowerrrr
 */
public class AjaxSlideTogglePanel extends Panel {

    private String contentId;

    public AjaxSlideTogglePanel(String id, Component content) {
        super(id);
        setOutputMarkupPlaceholderTag(true);
        add(new AttributeModifier("style", "display:none;"));
        content.setVisible(false);
        this.contentId = content.getId();
        add(content);
    }

    public void show(AjaxRequestTarget target) {
        getContent().setVisible(true);
        target.add(this);
        target.appendJavaScript(getSlideDownJS());

    }

    public final void hide(AjaxRequestTarget target) {
        getContent().setVisible(false);
        // use undocumented wicket feature to delay other ajax processing steps until animation completes.
        // see wicket-ajax.js#processEvaluation()
        String identifier = "processNext";
        String code = JQuery.slideUp(this, "slow", identifier + "()");
        target.prependJavaScript(identifier + "|" + code);
        target.add(this);
        onHide(target);
    }

    private CharSequence getSlideDownJS() {
        return JQuery.slideDown(this, "slow");
    }

    private CharSequence getSlideUpJS() {
        return JQuery.slideUp(this, "slow");
    }

    private Component getContent() {
        return get(contentId);
    }

    @Override
    public Markup getAssociatedMarkup() {
        return Markup.of("<wicket:panel><div wicket:id=\"" + contentId + "\" /></wicket:panel>");
    }

    public static void hideCurrent(Component component, AjaxRequestTarget target) {
        AjaxSlideTogglePanel panel = Check.notNull(component.findParent(AjaxSlideTogglePanel.class));
        panel.hide(target);
    }

    public void onHide(AjaxRequestTarget target) {
        ;
    }



}
