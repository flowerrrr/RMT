package de.flower.rmt.ui.manager.page.venues;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.util.geo.LatLng;
import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.app.RMTSession;
import de.flower.rmt.ui.common.form.CancelableEntityForm;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.TextAreaPanel;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.venues.map.GeocodePanel;
import de.flower.rmt.ui.manager.page.venues.map.VenueMapPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class VenueEditPanel extends BasePanel  {

    private EntityForm<Venue> form;

    private LatLng latLng;

    @SpringBean
    private IVenueManager venueManager;

    public VenueEditPanel(final IModel<Venue> model) {

        form = new CancelableEntityForm<Venue>("form", model) {

            @Override
            protected void onBeforeValidation(final Venue entity) {
                entity.setLatLng(latLng);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Venue> form) {
                venueManager.save(form.getModelObject());
                AjaxEventSender.entityEvent(this, Venue.class);
                onClose(target);
            }
        };
        add(form);

        form.add(new TextFieldPanel("name"));
        final TextAreaPanel address;
        form.add(address = new TextAreaPanel("address"));

        form.add(new GeocodePanel() {
            @Override
            protected String getAddress() {
                return address.getFormComponent().getValue();
            }
        }.setVisible(false)); // currently disable, feature not stable


        if (model.getObject().getLatLng() != null) {
            this.latLng = model.getObject().getLatLng();
        } else {
            this.latLng = RMTSession.get().getLatLng();
        }

        form.add(new VenueMapPanel(this.latLng, true) {
            @Override
            public void onUpdateMarker(LatLng latLng) {
                VenueEditPanel.this.latLng = latLng;
            }
        });
    }

}
