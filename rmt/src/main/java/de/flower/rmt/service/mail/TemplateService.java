package de.flower.rmt.service.mail;

import de.flower.common.util.Check;
import de.flower.rmt.util.Dates;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author flowerrrr
 */
@Service
public class TemplateService implements ITemplateService {

    private final static Logger log = LoggerFactory.getLogger(TemplateService.class);

    /** The Constant UTILMAP_NAME. */
    private static final String UTILMAP_NAME = "u";

    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * Autowiring maps does not work.
     */
    @Resource
    private Map<String, String> templateDefaults;

    /**
      * Merge template.
      *
      * @param template - relative to velocityEngine.resourcePath
      * @param input the model
      *
      * @return the merged template
      */
    @Override
     public String mergeTemplate(String template, Map<String, Object> input) {
        Check.notNull(template);

        // nicely formatted time string
        String currentTime = Dates.formatTimeMedium(new Date());
        String currentDate = Dates.formatDateLong(new Date());

        Map<String, Object> model = new HashMap<String, Object>(input);
        // add some default variables to context
        Map<String, String> utils = new HashMap<String, String>(templateDefaults);
        utils.put("time", currentTime);
        utils.put("date", currentDate);
        try {
            utils.put("hostname", InetAddress.getLocalHost().getCanonicalHostName());
        } catch (UnknownHostException e) {
            log.warn("Could not resolve hostname", e);
            utils.put("hostname", "[unknown host]");
        }
        // add to context
        if (model.containsKey(UTILMAP_NAME)) {
            throw new IllegalArgumentException("Cannot use context key '" + UTILMAP_NAME + "' in input model.");
        }
        model.put(UTILMAP_NAME, utils);

        String text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, template, model);

        return text;
    }
}
