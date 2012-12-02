package de.flower.rmt.ui.page.venues.manager.geocode;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.service.geocoding.GeocodingResult;
import de.flower.rmt.service.geocoding.IGeocodingService;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class GeocodePanel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(GeocodePanel.class);

    @SpringBean
    private IGeocodingService geocodingService;

    private IModel<List<GeocodingResult>> resultListModel = new ListModel<>();

    private GeocodePanel self = this;

    public GeocodePanel() {

        // need ajax submit button to have the address field submit its value so that it can be accessed here.
        AjaxSubmitLink geocodeButton = new AjaxSubmitLink("geocodeButton") {

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                String value = getAddress();
                if (StringUtils.isBlank(value)) {
                    // avoid useless remote call to google api
                    resultListModel.setObject(Collections.<GeocodingResult>emptyList());
                } else {
                    List<GeocodingResult> results;
                    try {
                        results = geocodingService.geocode(value);
                        resultListModel.setObject(results);
                    } catch (Exception e) {
                        log.error("Geocoding API error in search for [" + value + "]: " + e.getMessage(), e);
                        resultListModel.setObject(Collections.<GeocodingResult>emptyList());
                    }
                }
                self.setVisible(true);
                target.add(self);
                AjaxEventSender.send(this, GeocodingResult.class);
            }
        };
        // disable validation when clicking this button
        geocodeButton.setDefaultFormProcessing(false);
        getGeocodeButtonParent().add(geocodeButton);

        GeocodeResultsPanel geocodeResultsPanel;
        add(geocodeResultsPanel = new GeocodeResultsPanel(resultListModel) {
            @Override
            protected void onSelect(final AjaxRequestTarget target, final GeocodingResult result) {
                self.onSelect(target, result);
            }

            @Override
            public boolean isVisible() {
                return resultListModel.getObject() != null;
            }
        });
        geocodeResultsPanel.setOnCloseCallback(new BasePanel.IOnCloseCallback() {

            @Override
            public void onClose(final AjaxRequestTarget target) {
                self.setVisible(false);
                target.add(self);
            }
        });

    }

    protected abstract void onSelect(AjaxRequestTarget target, GeocodingResult result);

    /**
     * If add button should be placed outside of this panel hierarchy (waiting for Wicket 6).
     * @return
     */
    protected MarkupContainer getGeocodeButtonParent() {
        return this;
    }

    /**
     * Must return the address that is searched for.
     *
     * @return
     */
    protected abstract String getAddress();
}
