package de.flower.rmt.ui.common.page.event;

import de.flower.common.util.Check;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class EventPagerPanel extends BasePanel<Event> {

    public EventPagerPanel(final IModel<Event> model, final IModel<List<Event>> listModel) {
        super(model);
        WebMarkupContainer prev = createPagingLink("prev", new AbstractReadOnlyModel<Event>() {
            @Override
            public Event getObject() {
                return getPrevEvent(model, listModel);
            }
        });
        add(prev);
        add(DateLabel.forDateStyle("date", new PropertyModel(model, "date"), "S-"));
        add(DateLabel.forDateStyle("timeAsDate",  new PropertyModel(model, "timeAsDate"), "-S"));
        WebMarkupContainer next = createPagingLink("next", new AbstractReadOnlyModel<Event>() {
            @Override
            public Event getObject() {
                return getNextEvent(model, listModel);
            }
        });
        add(next);
    }

    private WebMarkupContainer createPagingLink(String id, final IModel<Event> model) {
        WebMarkupContainer container = new WebMarkupContainer(id);
        container.add(AttributeAppender.append("class", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (model.getObject() == null) {
                    return "disabled";
                }
                return "";
            }
        }));
        add(container);
        container.add(new Link("link") {
            @Override
            public boolean isVisible() {
                return model.getObject() != null;
            }

            @Override
            public void onClick() {
                EventPagerPanel.this.onClick(model);
            }
        });
        container.add(new WebMarkupContainer("noLink") {

            @Override
            public boolean isVisible() {
                return model.getObject() == null;
            }
        });
        return container;
    }

    private Event getNextEvent(final IModel<Event> model, final IModel<List<Event>> listModel) {
        List<Event> list = listModel.getObject();
        int index = list.indexOf(model.getObject());
        Check.isTrue(index != -1);
        if (index == list.size() - 1) {
            // no next element
            return null;
        } else {
            return list.get(index + 1);
        }
    }

    private Event getPrevEvent(final IModel<Event> model, final IModel<List<Event>> listModel) {
        List<Event> list = listModel.getObject();
        int index = list.indexOf(model.getObject());
        Check.isTrue(index != -1);
        if (index == 0) {
            // no prev element
            return null;
        } else {
            return list.get(index - 1);
        }
    }

    protected abstract void onClick(IModel<Event> model);
}
