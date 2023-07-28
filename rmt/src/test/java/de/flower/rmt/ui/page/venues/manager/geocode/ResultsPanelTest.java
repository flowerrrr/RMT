package de.flower.rmt.ui.page.venues.manager.geocode;

import de.flower.common.util.geo.LatLng;
import de.flower.rmt.service.geocoding.GeocodingResult;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class ResultsPanelTest extends AbstractRMTWicketMockitoTests {


    @Test
    public void testRender() {
        IModel<List<GeocodingResult>> listModel = new ListModel(new ArrayList());
        for (int i = 0; i < 3; i++) {
            GeocodingResult result = new GeocodingResult();
            result.setFormatted_address(RandomStringUtils.randomAlphabetic(20));
            result.setLatLng(new LatLng(RandomUtils.nextDouble(), RandomUtils.nextDouble()));
            listModel.getObject().add(result);
        }
        wicketTester.startComponentInPage(new TestGeocodeResultsPanel(listModel));
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testRenderEmptyList() {
        ListModel listModel = new ListModel(new ArrayList());
        wicketTester.startComponentInPage(new TestGeocodeResultsPanel(listModel));
        wicketTester.dumpComponentWithPage();
    }

    private static class TestGeocodeResultsPanel extends GeocodeResultsPanel {

        public TestGeocodeResultsPanel(final IModel<List<GeocodingResult>> listModel) {
            super(listModel);
        }

        @Override
        protected void onSelect(final AjaxRequestTarget target, final GeocodingResult modelObject) {

        }
    }
}
