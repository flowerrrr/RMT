package de.flower.rmt.ui.app;

/**
 * @author flowerrrr
 */
// TODO (flowerrrr - 21.04.12) replace with ContextRelativeResource and use wickets caching support.
public interface Resource {

    public static final String bootstrapJsUrl = "static/bootstrap/js/bootstrap.min.js?" + Version.VERSION;

    public static final String lessJsUrl = "static/js/less-1.3.0.min.js";

    public static final String jqueryJsUrl = "http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js";

    public static final String datepickerJsUrl = "static/js/bootstrap/bootstrap-datepicker.js?" + Version.VERSION;

    public static final String datepickerCssUrl = "static/css/bootstrap/datepicker.css?" + Version.VERSION;

    public static final String bootstrapCssUrl = "static/bootstrap/css/bootstrap.min.css?" + Version.VERSION;

    public static final String mainCssUrl = "static/css/main.css?" + Version.VERSION;

    public static final String ieCssUrl = "static/css/ie.css?" + Version.VERSION;

    public static final String touchCssUrl = "static/css/touch.css?" + Version.VERSION;

    public static final String mainJsUrl = "static/js/main.js?" + Version.VERSION;

    public static final String searchFilterJsUrl = "static/js/search-filter.js?" + Version.VERSION;

    public static final String lessLink = "<link href=\"%s\" rel=\"stylesheet/less\" type=\"text/css\"/>";
}
