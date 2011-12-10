package de.flower.rmt.service.mail;

import java.util.Map;

/**
 * @author flowerrrr
 */
public interface ITemplateService {

    String mergeTemplate(String template, Map<String,Object> model);
}
