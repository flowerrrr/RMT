package de.flower.common.ui.markup.html.basic;

import de.flower.common.ui.model.FallbackModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;


public class FallbackLabel extends Label {

    private IModel<?> defaultModel;

    public FallbackLabel(final String id, final IModel<?> model, final IModel<?> defaultModel) {
        super(id, model);
        this.defaultModel = defaultModel;
    }

    public FallbackLabel(final String id, final IModel<?> defaultModel) {
        this(id, null, defaultModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setDefaultModel(new FallbackModel(defaultModel, getDefaultModel()));
    }
}
