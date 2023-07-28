package de.flower.common.ui.model;

import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IModel;


public abstract class AbstractChainingModel<S, T> implements IChainingModel<S> {

    private IModel<T> chainedModel;

    public AbstractChainingModel(final IModel<T> chainedModel) {
        this.chainedModel = chainedModel;
    }

    @Override
    public void detach() {
        chainedModel.detach();
    }

    @Override
    public IModel<T> getChainedModel() {
        return chainedModel;
    }

    @Override
    public void setChainedModel(final IModel<?> model) {
        this.chainedModel = (IModel<T>) model;
    }

    public T getChainedModelObject() {
        return getChainedModel().getObject();
    }
}
