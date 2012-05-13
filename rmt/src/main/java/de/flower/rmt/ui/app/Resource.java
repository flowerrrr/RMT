package de.flower.rmt.ui.app;

/**
 * @author flowerrrr
 */
// TODO (flowerrrr - 21.04.12) replace with ContextRelativeResource and use wickets caching support.
public interface Resource {

    public static final String bootstrapJsUrl = "bootstrap/js/bootstrap.js?" + Version.VERSION;

    public static final String lessJsUrl = "js/less-1.3.0.min.js";

    public static final String jqueryJsUrl = "http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js";

    public static final String datepickerJsUrl = "js/bootstrap/bootstrap-datepicker.js?" + Version.VERSION;

    public static final String bootstrapCssUrl = "bootstrap/css/bootstrap.min.css?" + Version.VERSION;

    public static final String mainCssUrl = "css/main.css?" + Version.VERSION;

    public static final String ieCssUrl = "css/ie.css?" + Version.VERSION;

    public static final String mainJsUrl = "js/main.js?" + Version.VERSION;
}
