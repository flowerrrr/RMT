package de.flower.rmt.ui.manager.page.venues;

import de.flower.common.ui.FormMode;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.Event;
import de.flower.common.ui.form.MyForm;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.common.util.geo.LatLngEx;
import de.flower.rmt.model.Venue;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.service.geocoding.GeocodingResult;
import de.flower.rmt.service.geocoding.IGeocodingService;
import de.flower.rmt.ui.app.RMTSession;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.venues.panel.GMapPanel2;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;
import wicket.contrib.gmap3.GMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oblume
 */
public class VenueEditPanel extends BasePanel {

    private FormMode mode;

    private Form<Venue> form;

    private GMapPanel2 mapPanel;

    private WebMarkupContainer geocodeNoResults;

    private WebMarkupContainer geocodeResultListContainer;

    private LatLngEx latLng;

    @SpringBean
    private IVenueManager venueManager;

    @SpringBean
    private ISecurityService securityService;

    @SpringBean
    private IGeocodingService geocodingService;

    public VenueEditPanel(String id) {
        super(id);

        form = new MyForm<Venue>("form", new Venue());
        add(form);

        form.add(new ValidatedTextField("name"));
        final TextArea address;
        form.add(address = new TextArea("address"));


        form.add(geocodeNoResults = new WebMarkupContainer("geocodeNoResult"));
        geocodeNoResults.setOutputMarkupPlaceholderTag(true);
        geocodeNoResults.setVisible(false);

        form.add(geocodeResultListContainer = new WebMarkupContainer("geocodeResultListContainer"));
        geocodeResultListContainer.setOutputMarkupPlaceholderTag(true);
        final ListView<GeocodingResult> geocodeResultList = new ListView<GeocodingResult>("geocodeResultList", new ArrayList<GeocodingResult>()) {
            @Override
            protected void populateItem(ListItem<GeocodingResult> item) {
                final GeocodingResult geocodingResult = item.getModelObject();
                item.add(new Label("address", geocodingResult.getAddress()));
                item.add(new AjaxLink("chooseButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        latLng = geocodingResult.getLatLng();
                        // update marker on map
                        mapPanel.init(Model.of(latLng));
                        target.add(mapPanel);
                    }
                });
            }
        };
        geocodeResultListContainer.add(geocodeResultList);

       // component to translate address into latlng
        // if multiple results are returned list them and let user choose. after choosing the marker
        // is set on the map. user can still choose other result.
        AjaxSubmitLink geocodeButton;
        form.add(geocodeButton = new MyAjaxSubmitLink("geocodeButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                String value = address.getValue();
                if (StringUtils.isBlank(value)) {
                    return;
                }
                List<GeocodingResult> results = geocodingService.geocode(value);
                geocodeResultList.setModelObject(results);
                if (results.size() == 0) {
                    // tell user he has to refine address or manually set marker
                    geocodeNoResults.setVisible(true);
                    geocodeResultListContainer.setVisible(false);
//                } else if (results.size() == 1) {
//                    // automatically set marker
                } else {
                    geocodeNoResults.setVisible(false);
                    geocodeResultListContainer.setVisible(true);
                }
                target.add(geocodeNoResults);
                target.add(geocodeResultListContainer);
            }
        });
        geocodeButton.setDefaultFormProcessing(false);

        form.add(new MyAjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Venue venue = (Venue) form.getModelObject();
                venue.setLatLng(latLng);
                if (!new BeanValidator(form).isValid(venue)) {
                    onError(target, form);
                } else {
                    venueManager.save(venue);
                    target.registerRespondListener(new AjaxRespondListener(Event.EntityCreated(Venue.class), Event.EntityUpdated(Venue.class)));
                    ModalWindow.closeCurrent(target);
                }
            }
        });

        form.add(mapPanel = new GMapPanel2("mapPanel", RMTSession.get().getLatLng()) {
            @Override
            public void onUpdateMarker(LatLngEx latLng) {
                VenueEditPanel.this.latLng = latLng;
            }
        });
        mapPanel.setOutputMarkupId(true);
    }

    public void init(IModel<Venue> model) {
        if (model == null) {
            model = Model.of(venueManager.newVenueInstance());
        }
        form.setModel(new CompoundPropertyModel<Venue>(model));
        mapPanel.init(Model.of(model.getObject().getLatLng()));
        hideGeocodingResults();
    }

    public GMap getGMap() {
        return mapPanel.getGMap();
    }

    private void hideGeocodingResults() {
        geocodeNoResults.setVisible(false);
        geocodeResultListContainer.setVisible(false);
    }
}
