package de.flower.common.ui.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Model for labels that returns a fallback model when default model returns empty or blank object.
 */
public class FallbackModel<T> extends AbstractReadOnlyModel<T> {

    private final static Logger log = LoggerFactory.getLogger(FallbackModel.class);

    private IModel<T> fallbackModel;

    private IModel<T> targetModel;

    public FallbackModel(IModel<T> fallbackModel, IModel<T> targetModel) {
        this.fallbackModel = fallbackModel;
        this.targetModel = targetModel;
    }

    @Override
    public T getObject() {
        T object = targetModel.getObject();
        if (isEmptyOrBlank(object)) {
            return fallbackModel.getObject();
        } else {
            return object;
        }
    }

    private boolean isEmptyOrBlank(T object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return StringUtils.isBlank((CharSequence) object);
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else {
            log.warn("Unknown type [" + object.getClass() + "]. cannot check for empty or blank");
            return false;
        }
    }

    @Override
    public void detach() {
        fallbackModel.detach();
        targetModel.detach();
        super.detach();
    }
}
