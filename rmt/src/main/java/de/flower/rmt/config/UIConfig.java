package de.flower.rmt.config;

import de.flower.common.ui.serialize.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.wicketstuff.jsr303.spring.JSR303SpringConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {
        "de.flower.rmt.ui",
        "de.flower.common.ui"
})
@Import(JSR303SpringConfig.class)
public class UIConfig {

    @Bean
    public Filter filter() {
        Filter filter = new Filter();
        filter.setBlackList(Arrays.asList("de\\.flower\\.rmt\\.model\\.db\\.entity\\..*", "ch\\.qos\\.logback\\.classic\\.Logger"));
        filter.setWhiteList(Arrays.asList(
                ".*Behavior", ".*Panel", ".*Label", ".*EntityForm", ".*FormComponent", ".*Page",
                ".*Model", ".*DataProvider", "org\\.apache\\.wicket\\..*", "de.flower.common.ui.ajax.event.AjaxEventListener",
                "de.flower.common.ui.ajax.markup..*", "de.flower.common.ui.markup..*", "de.flower.common.ui.modal.ModalDialogWindow",
                "de.flower.common.ui.modal.ModalDialogWindowPanel", "de.flower.common.util.geo.LatLng", "de.flower.rmt.model.dto..*",
                "de.flower.rmt.ui..*Message", "de.flower.rmt.ui.markup.html.form..*",
                "de.flower.rmt.ui.page.event.EventDetailsPanel\\$SurfaceListLabel",
                "de.flower.rmt.ui.page.event.EventDetailsPanel\\$UniformLabel", "de.flower.rmt.ui.panel.QuickResponseLabel",
                "de.flower.rmt.ui.panel..*", "java.lang.Object", "java.util.AbstractList",
                "javax.mail.internet.InternetAddress", "org.wicketstuff.jsr303.validator.FormComponentBeanValidator",
                "org.wicketstuff.jsr303.validator.PropertyValidator", "de.flower.common.util.geo.LatLng",
                "wicket.contrib.gmap3..*", "com.google.common.collect.*",
                "org.joda.time.LocalTime", "org.joda.time.tz.FixedDateTimeZone",
                "java.util.Arrays\\$ArrayList"));
        return filter;
    }

    @Bean
    public Map<String, String> page404RedirectMapping() {
        Map<String, String> page404RedirectMapping = new HashMap<>();
        page404RedirectMapping.put("/event", "/events");
        page404RedirectMapping.put("/player/event/", "/events");
        return page404RedirectMapping;
    }
}
