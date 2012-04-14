package de.flower.rmt.ui.page.venues.manager.map;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.rmt.service.geocoding.GeocodingResult;
import de.flower.rmt.service.geocoding.IGeocodingService;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class GeocodePanel extends BasePanel {

    private WebMarkupContainer geocodeNoResults;

    private WebMarkupContainer geocodeResultListContainer;

    private boolean searched;

    @SpringBean
    private IGeocodingService geocodingService;

    public GeocodePanel() {

        add(geocodeResultListContainer = new WebMarkupContainer("geocodeResultListContainer"));
        geocodeResultListContainer.setOutputMarkupPlaceholderTag(true);
        final ListView<GeocodingResult> geocodeResultList = new ListView<GeocodingResult>("geocodeResultList", new ArrayList<GeocodingResult>()) {
            @Override
            protected void populateItem(ListItem<GeocodingResult> item) {
                final GeocodingResult geocodingResult = item.getModelObject();
                item.add(new Label("address", geocodingResult.getAddress()));
                item.add(new AjaxLink("chooseButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        throw new UnsupportedOperationException("Feature not implemented!");
//                        latLng = geocodingResult.getLatLng();
//                        // update marker on map
//                        mapPanel.setMarker(latLng);
//                        target.add(mapPanel);
                    }
                });
            }

            /**
             * To allow usage of wicket:enclosure for containing table.
             * @return
             */
            @Override
            public boolean isVisible() {
                return this.getList().size() > 0 && searched;
            }
        };
        geocodeResultListContainer.add(geocodeResultList);

        add(geocodeNoResults = new WebMarkupContainer("geocodeNoResult") {
            @Override
            public boolean isVisible() {
                return geocodeResultList.getList().isEmpty() && searched;
            }
        });
        geocodeNoResults.setOutputMarkupPlaceholderTag(true);


       // component to translate address into latlng
        // if multiple results are returned list them and let user choose. after choosing the marker
        // is set on the map. user can still choose other result.
        org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink geocodeButton;
        add(geocodeButton = new AjaxSubmitLink("geocodeButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                String value = getAddress();
                if (StringUtils.isBlank(value)) {
                    searched = false;
                    return;
                }
                searched = true;
                List<GeocodingResult> results = geocodingService.geocode(value);
                geocodeResultList.setModelObject(results);
                target.add(geocodeNoResults);
                target.add(geocodeResultListContainer);
            }
        });
        // disable validation when clicking this button
        geocodeButton.setDefaultFormProcessing(false);

    }

    @Override
    protected void onDetach() {
        super.onDetach();
        searched = false;
    }

    /**
     * Must return the address that is searched for.
     * @return
     */
    protected abstract String getAddress();
}
