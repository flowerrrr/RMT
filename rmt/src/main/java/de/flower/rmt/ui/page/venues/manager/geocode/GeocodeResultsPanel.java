package de.flower.rmt.ui.page.venues.manager.geocode;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.service.geocoding.GeocodingResult;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class GeocodeResultsPanel extends BasePanel<List<GeocodingResult>> {

    public GeocodeResultsPanel(final IModel<List<GeocodingResult>> listModel) {
        super(listModel);

        add(new AjaxEventListener(GeocodingResult.class));
        add(new WebMarkupContainer("noResult") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });

        add(new AjaxLink("closeButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                onClose(target);
            }
        });

        final ListView<GeocodingResult> resultList = new ListView<GeocodingResult>("resultList", listModel) {
            @Override
            protected void populateItem(ListItem<GeocodingResult> item) {
                item.add(new AjaxLink<GeocodingResult>("chooseLink", item.getModel()) {
                    {
                        add(new Label("address", getModelObject().getAddress()));
                    }

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        onSelect(target, getModelObject());
                    }
                });
            }

            /**
             * To allow usage of wicket:enclosure for containing table.
             *
             * @return
             */
            @Override
            public boolean isVisible() {
                return !listModel.getObject().isEmpty();
            }
        };
        add(resultList);
    }

    protected abstract void onSelect(final AjaxRequestTarget target, final GeocodingResult modelObject);
}
