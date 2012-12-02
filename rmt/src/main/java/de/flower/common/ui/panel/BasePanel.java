package de.flower.common.ui.panel;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import de.flower.common.util.Clazz;
import de.flower.common.util.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * @author flowerrrr
 */
public class BasePanel<T> extends GenericPanel<T> {

    private static final Logger log = LoggerFactory.getLogger(BasePanel.class);

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
        super(getId(id, BasePanel.class), model);
        if (log.isTraceEnabled()) log.trace("new " + getId());
        setOutputMarkupId(true);
        // always append a css class to the panels
        add(new AttributeAppender("class", Model.of(getCssClasses()), " "));
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

    protected String getPanelMarkup() {
        return null;
    }

    @Override
    public Markup getAssociatedMarkup() {
        String markup = getPanelMarkup();
        if (markup != null) {
            return Markup.of("<wicket:panel>" + markup + "</wicket:panel>");
        } else {
            return super.getAssociatedMarkup();
        }
    }

    /**
     * Most panels let the basepanel determine the id. using default id provides a
     * good naming strategy in your code.
     * Must use static method cause it is used in super() call.
     *
     */
    protected static String getId(String id, final Class<?> callee) {
        if (id != null) {
            return id;
        } else {
            Class<?> thisClass = Clazz.getThisClassStatic(callee);
            if (Clazz.isAnonymousClass(thisClass)) {
                thisClass = Clazz.getSuperClass(thisClass);
            }
            String className = Clazz.getShortName(thisClass);
            return Strings.uncapitalize(className);
        }
    }

    @VisibleForTesting
    protected String getCssClasses() {
        List<Class<?>> panelClasses = Clazz.getClassList(this.getClass(), BasePanel.class);
        panelClasses = Lists.newArrayList(Collections2.filter(panelClasses, new Predicate<Class<?>>() {
            @Override
            public boolean apply(final Class<?> input) {
                return !Clazz.isAnonymousClass(input);
            }
        }));
        List<String> cssClasses = Lists.transform(panelClasses, new Function<Class<?>, String>() {
            @Override
            public String apply(final Class<?> input) {
                return getCssClass(input);
            }
        });
        return StringUtils.join(cssClasses, " ");
    }

    public static String getCssClass(Class<?> clazz) {
        // return Strings.camelCaseToHyphen(getClassName(clazz)).toLowerCase();
        return getClassName(clazz);
    }

    private static String getClassName(Class<?> clazz) {
        if (Clazz.isAnonymousClass(clazz)) {
            return Clazz.getSuperClass(clazz).getSimpleName();
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
