package de.flower.common.ui.ajax.markup.html;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Overrides some default behavior.
 */
public class AjaxEditableLabelExtended<T> extends AjaxEditableLabel<T> {

    public AjaxEditableLabelExtended(final String id, final IModel<T> iModel) {
        super(id, iModel);
        add(AttributeModifier.append("class", "AjaxEditableLabel"));
        getLabel().add(AttributeModifier.append("class", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return getCssClass(AjaxEditableLabelExtended.this.isEnabled());
            }
        }));
        getLabel().add(AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return AjaxEditableLabelExtended.this.isEnabled() ? new ResourceModel("button.edit").getObject() : "";
            }
        }));
    }

    protected String getCssClass(boolean enabled) {
        return enabled ? "enabled" : "disabled";
    }
}
