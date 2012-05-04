package de.flower.common.ui.ajax.markup.repeater;

import de.flower.common.model.IIdentifiable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Refreshing view that can be partially updated by ajax calls (appends new elements to end of list).
 *
 * In html:
 *
 * <pre>
 *   <ul wicket:id="container">
 *      <li wicket:id="list">
 *          <span wicket:id="name" />
 *          ....
 *      </li>
 *   </ul>
 * </pre>
 *
 * In java:
 *
 * <pre>
 * WebMarkupContainer container = new WebMarkupContainer("container");
 * RefreshingView listView = new AjaxAppendingRefreshingView("list") {
 *     ....
 * }
 * </pre>
 *
 * Append new items to list as part of an ajax response
 *
 * <pre>
 * AjaxLink link = new AjaxLink() {
 *    public void onClick(AjaxRequestTarget target) {
 *        listView.appendNewItems(target, getNewItems());
 *    }
 * }
 * </pre>
 *
 * Or let list update itself with ajax timer behavior. List will detect new items.
 *
 * <pre>
 * container.add(new AbstractAjaxTimerBehavior(Duration.seconds(10)) {
 *      public void onTimer(AjaxRequestTarget target) {
 *          listView.updateItems(target);
 *      }
 * }
 *
 * </pre>
 *
 * @see http://wicketinaction.com/2008/10/repainting-only-newly-created-repeater-items-via-ajax/
 * @see http://donteattoomuch.blogspot.com/2008/04/partial-ajax-update-capable-list-view.html
 *
 * @param <T> generic type of list elements.
 */
public abstract class AjaxAppendingRefreshingView<T extends IIdentifiable<?>> extends RefreshingView<T> {

    private int currentIndex;

    private final List<Serializable> idList = new ArrayList<Serializable>();

    /**
     * Instantiates a new self appending refreshing view.
     *
     * @param id the id
     */
    public AjaxAppendingRefreshingView(final String id) {
        super(id);
    }


    /**
     * Instantiates a new self appending refreshing view.
     *
     * @param id the id
     * @param model the model
     */
    public AjaxAppendingRefreshingView(final String id, final IModel<?> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        getParent().setOutputMarkupId(true);
    }

    /**
     * Iterates over new items and appends them to the existing list.
     *
     * @param target the target
     * @param newItems the new items
     */
    public final void appendNewItems(final AjaxRequestTarget target, final Iterator<IModel<T>> newItems) {
        for (final Iterator<IModel<T>> iterator = newItems; iterator.hasNext(); ) {
            IModel<T> model = iterator.next();
            final String id = newChildId();
            final Item<T> item = newItem(id, currentIndex + 1, model);
            populateItem(item);
            item.setOutputMarkupId(true);
            add(item);
            target.prependJavaScript(getJavaScriptToRenderNewDomElement(item));
            target.add(item);
        }
    }

    /**
     * Update items. List will call {@link #getListItems()} and determine which items are new and add those new items to
     * the list.
     *
     * @param target the target
     */
    public final void updateItems(final AjaxRequestTarget target) {
        final List<IModel<T>> newItems = new ArrayList<IModel<T>>();
        for (final Iterator<IModel<T>> iterator = getItemModels(); iterator.hasNext(); ) {
            final IModel<T> itemModel = iterator.next();
            final T object = itemModel.getObject();
            if (!idList.contains(object.getId())) {
                newItems.add(itemModel);
            }
        }
        if (!newItems.isEmpty()) {
            appendNewItems(target, newItems.iterator());
        }
    }

    private String getJavaScriptToRenderNewDomElement(final Item<T> item) {
        // can use any element tag. does not have to match list type. will be replaced by wicket ajax-update.
        final String script = String.format("var item=document.createElement('%s');item.id='%s';"
                + "Wicket.$('%s').appendChild(item);", "span", item.getMarkupId(), getParent().getMarkupId());
        return script;
    }

    @Override
    protected final Item<T> newItem(final String id, final int index, final IModel<T> model) {
        // must track current index.
        this.currentIndex = index;
        // also track ids of rendered items to be able to detect new items when updating list.
        idList.add(model.getObject().getId());
        return super.newItem(id, index, model);
    }

}
