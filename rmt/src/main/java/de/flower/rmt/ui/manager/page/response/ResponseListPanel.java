package de.flower.rmt.ui.manager.page.response;

import de.flower.common.util.Check;
import de.flower.rmt.model.Player;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IPlayerManager;
import de.flower.rmt.service.IResponseManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
// TODO (flowerrrr - 23.10.11) unite with players ResponseListPanel
public class ResponseListPanel extends BasePanel {

    @SpringBean
    private IResponseManager responseManager;

    @SpringBean
    private IPlayerManager playerManager;

    public ResponseListPanel(String id, LoadableDetachableModel<Event> model) {
        super(id);
        Check.notNull(model.getObject());

        add(createListView("acceptedList", RSVPStatus.ACCEPTED, model));
        add(createListView("unsureList", RSVPStatus.UNSURE, model));
        add(createListView("declinedList", RSVPStatus.DECLINED, model));

        ListView list = new ListView<Player>("noresponseList", getNotResponderList(model)) {
            @Override
            protected void populateItem(ListItem<Player> item) {
                item.add(new Label("name", item.getModelObject().getFullname()));
            }
        };
        add(list);
    }

    private Component createListView(String id, RSVPStatus status, IModel<Event> model) {
        ListView list = new ListView<Response>(id, getResponseList(model, status)) {
            @Override
            protected void populateItem(ListItem<Response> item) {
                item.add(createResponseFragement(item));
            }
        };
        return list;
    }

    private Component createResponseFragement(ListItem<Response> item) {
        final Response response = item.getModelObject();
        Fragment frag = new Fragment("itemPanel", "itemFragment", this);
        frag.add(new Label("name", response.getName()));
        frag.add(DateLabel.forDateStyle("date", Model.of(response.getDate()), "SS"));
        return frag;
    }

    private IModel<List<Response>> getResponseList(final IModel<Event> model, final RSVPStatus rsvpStatus) {
        return new LoadableDetachableModel<List<Response>>() {
            @Override
            protected List<Response> load() {
                return responseManager.findByEventAndStatus(model.getObject(), rsvpStatus);
            }
        };
    }

    private IModel<List<Player>> getNotResponderList(final IModel<Event> model) {
        return new LoadableDetachableModel<List<Player>>() {
            @Override
            protected List<Player> load() {
                return playerManager.findNotResponded(model.getObject());
            }
        };
    }

}
