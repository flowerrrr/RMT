package de.flower.rmt.ui.manager.page.response;

import de.flower.common.util.Check;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IResponseManager;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class ResponsePanel extends Panel {

    @SpringBean
    private IResponseManager responseManager;

    public ResponsePanel(String id, LoadableDetachableModel<Event> model) {
        super(id);
        Check.notNull(model.getObject());

        ListView acceptedList = new ListView<Response>("acceptedList", getResponseList(model, RSVPStatus.ACCEPTED)) {
            @Override
            protected void populateItem(ListItem<Response> item) {
                final Response response = item.getModelObject();
                Fragment frag = new Fragment("itemPanel", "itemFragment", this.getParent());
                frag.add(new Label("name", response.getName()));
                item.add(frag);
            }
        };
        add(acceptedList);
    }

    private IModel<? extends List<? extends Response>> getResponseList(final IModel<Event> model, final RSVPStatus rsvpStatus) {
        return new LoadableDetachableModel<List<? extends Response>>() {
            @Override
            protected List<? extends Response> load() {
                return responseManager.findByEventAndStatus(model.getObject(), rsvpStatus);
            }
        };
    }
}
