package de.flower.rmt.ui.manager.page.venues;

import de.flower.common.ui.FormMode;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.Event;
import de.flower.common.ui.form.MyForm;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.common.util.geo.LatLngEx;
import de.flower.rmt.model.Venue;
import de.flower.rmt.service.ISecurityService;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.app.RMTSession;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.venues.panel.GMapPanel2;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;
import wicket.contrib.gmap3.GMap;

/**
 * @author oblume
 */
public class VenueEditPanel extends BasePanel {

    private FormMode mode;

    private Form<Venue> form;

    private GMapPanel2 mapPanel;

    private LatLngEx latLng;

    @SpringBean
    private IVenueManager venueManager;

    @SpringBean
    private ISecurityService securityService;

    public VenueEditPanel(String id) {
        super(id);

        form = new MyForm<Venue>("form", new Venue());
        add(form);

        form.add(new ValidatedTextField("name"));
        form.add(new TextArea("address"));

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

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });

        form.add(mapPanel = new GMapPanel2("gmap", RMTSession.get().getLatLng()) {
            @Override
            public void onUpdateMarker(LatLngEx latLng) {
                VenueEditPanel.this.latLng = latLng;
            }
        });
    }

    public void init(IModel<Venue> model) {
        if (model == null) {
            model = Model.of(venueManager.newVenueInstance());
        }
        form.setModel(new CompoundPropertyModel<Venue>(model));
        mapPanel.init(Model.of(model.getObject().getLatLng()));
    }

    public GMap getGMap() {
        return mapPanel.getGMap();
    }
}
