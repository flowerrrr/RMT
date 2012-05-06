package de.flower.common.ui.panel;

import de.flower.common.util.Clazz;
import de.flower.common.util.Strings;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
public class BasePanel<T> extends GenericPanel<T> {

    protected static final Logger log = LoggerFactory.getLogger(BasePanel.class);
    private IOnCloseCallback onCloseCallback;

    public BasePanel() {
        this(null, null);
    }

    public BasePanel(IModel<T> model) {
        this(null, model);
    }

    protected BasePanel(String id) {
        this(id, null);
    }

    protected BasePanel(String id, IModel<T> model) {
        super(getId(id), model);
        if (log.isTraceEnabled()) log.trace("new " + getId());
        setOutputMarkupId(true);
        // always append a css class to the panels
        add(new AttributeAppender("class", Model.of("panel " + getCssClass()), " "));
    }

    /**
     * Should be called by a panel that can be dismissed/closed, like in modal windows or panels that should be hidden
     * when editing is finished.
     */
    protected void onClose(AjaxRequestTarget target) {
        if (onCloseCallback != null) {
            onCloseCallback.onClose(target);
        }
    }


    /**
     * Most panels let the basepanel determine the id. using default id provides a
     * good naming strategy in your code.
     * @param id
     * @return
     */
    protected static String getId(String id) {
        if (id != null) {
            return id;
        } else {
            String className = Clazz.getCallingClassStatic().getSimpleName();
            return Strings.uncapitalize(className);
        }
    }

    private String getCssClass() {
        return getCssClass(getClass());
    }

    public static String getCssClass(Class<?> clazz) {
        return Strings.camelCaseToHyphen(getClassName(clazz)).toLowerCase();
    }

    private static String getClassName(Class<?> clazz) {
        if (Clazz.isAnonymousInnerClass(clazz)) {
            return Clazz.getSuperClassName(clazz);
        } else {
            return clazz.getSimpleName();
        }
    }

    public IOnCloseCallback getOnCloseCallback() {
        return onCloseCallback;
    }

    public void setOnCloseCallback(final IOnCloseCallback onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    public interface IOnCloseCallback extends Serializable {

        void onClose(AjaxRequestTarget target);
    }
}
