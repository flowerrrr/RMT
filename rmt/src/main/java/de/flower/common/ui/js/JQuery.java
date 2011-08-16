package de.flower.common.ui.js;

import de.flower.common.util.Check;
import org.apache.wicket.Component;

/**
 * @author oblume
 */
public class JQuery {

    public static String slideDown(Component component, String duration) {
        Check.notBlank(component.getMarkupId());
        return "$('#" + component.getMarkupId() + "').slideDown('" + duration + "')";
    }

    public static String slideUp(Component component, String duration) {
        Check.notBlank(component.getMarkupId());
        return "$('#" + component.getMarkupId() + "').slideUp('" + duration + "')";
    }

    public static String slideUp(Component component, String duration, String callback) {
        Check.notBlank(component.getMarkupId());
        return "$('#" + component.getMarkupId() + "').slideUp('" + duration + "', function() { " + callback + "; })";
    }

    public static String fadeOut(Component component, String duration) {
        Check.notBlank(component.getMarkupId());
        return "$('#" + component.getMarkupId() + "').animate({opacity:0},'" + duration + "', function() { $('#" +  component.getMarkupId()  + "').css({visibility: 'hidden'}); })";
    }

    public static String fadeIn(Component component, String duration) {
        Check.notBlank(component.getMarkupId());
        return "$('#" + component.getMarkupId() + "').css({visibility: 'visible'}); $('#" + component.getMarkupId() + "').animate({opacity:1},'" + duration + "')";
    }
}
