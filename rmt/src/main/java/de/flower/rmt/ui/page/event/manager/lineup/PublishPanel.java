package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
* @author flowerrrr
*/
public abstract class PublishPanel<T> extends BasePanel<T> {

    public PublishPanel(IModel<T> model) {
        super(model);
        add(new AjaxEventListener(PublishPanel.this));
        add(new AjaxLink("publishButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                publish(target);
                AjaxEventSender.send(this, PublishPanel.this);
            }

            @Override
            public boolean isVisible() {
                return !isPublished();
            }
        });
        add(new WebMarkupContainer("publishedMessage") {
            @Override
            public boolean isVisible() {
                return isPublished();
            }
        });
    }

    protected abstract void publish(AjaxRequestTarget target);

    protected abstract boolean isPublished();
}
