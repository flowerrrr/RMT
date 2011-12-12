package de.flower.common.ui.inline;

import de.flower.common.util.Check;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Inline editable label that switches into a dropdownbox if clicked.
 * <p/>
 * Showing/hiding of label and select box is done client side to have better performance than
 * {@link org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel} offers.
 *
 * @author flowerrrr
 */
@Deprecated // component does not update model when onchange event is triggered.
public abstract class DropDownChoiceLabel<T> extends Panel {

    public DropDownChoiceLabel(String id, IModel<T> model, final List<? extends T> choices) {
        this(id, model, choices, null);
    }

    public DropDownChoiceLabel(String id, final IModel<T> model, final List<? extends T> choices, final IChoiceRenderer<? super T> choiceRenderer) {
        super(id);
        Check.notNull(model);

        Label label = new Label("label", model);
        label.setOutputMarkupId(true);
        add(label);

        DropDownChoice select = new DropDownChoice<T>("select", model, choices, choiceRenderer);
        select.add(new AjaxEventBehavior("onchange") {
            @Override
            protected void onEvent(final AjaxRequestTarget target) {
                T newSelection = model.getObject();
                DropDownChoiceLabel.this.onSelectionChanged(newSelection);
            }
        });

        select.setOutputMarkupId(true);
        add(select);

        // add javascript handler to show/hide elements
        String jsLabel = "this.style.display='none';$('#" + select.getMarkupId() + "').style.display='inline';";
        label.add(AttributeModifier.replace("onclick", jsLabel));
        // select.add(AttributeModifier.replace("onclick", jsLabel));
    }

    /**
     * Implement this method to get notified when selection changes.
     *
     * @param newSelection
     */
    protected abstract void onSelectionChanged(final T newSelection);
}
