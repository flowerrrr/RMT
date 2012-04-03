package de.flower.common.ui.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

/**
 * @author flowerrrr
 */
public abstract class AbstractWrappingModel<S, T> implements IWrapModel<S> {

    private IModel<T> wrappedModel;

    public AbstractWrappingModel(final IModel<T> wrappedModel) {
        this.wrappedModel = wrappedModel;
    }

    @Override
    public void detach() {
        wrappedModel.detach();
    }

    @Override
    public IModel<T> getWrappedModel() {
        return wrappedModel;
    }

    public T getWrappedModelObject() {
        return getWrappedModel().getObject();
    }
}
