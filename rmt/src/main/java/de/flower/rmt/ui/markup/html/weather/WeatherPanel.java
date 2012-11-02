package de.flower.rmt.ui.markup.html.weather;

import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author flowerrrr
 */
public class WeatherPanel extends BasePanel<String> {

    private static final String url = "http://www.wetteronline.de/cgi-bin/hpweather?PLZ={0}&FORMAT=long&MENU=dropdown&MAP=rainradar";

    public WeatherPanel(final IModel<String> addressModel) {
        IModel<String> iframeSrcModel = getIframeSrcModel(addressModel);
        setModel(iframeSrcModel);

        WebMarkupContainer iframe = new WebMarkupContainer("iframe");
        iframe.add(AttributeModifier.replace("src", iframeSrcModel));
        add(iframe);
    }

    @Override
    public boolean isVisible() {
        return getModelObject() != null;
    }

    private IModel<String> getIframeSrcModel(final IModel<String> addressModel) {
        return new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                String address = addressModel.getObject();
                String zipCode = getZipCode(address);
                return (zipCode == null) ? null : MessageFormat.format(url, zipCode);
            }
        };
    }

    public static String getZipCode(String address) {
        if (address == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\d{5}");
        Matcher m = pattern.matcher(address);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }


}
