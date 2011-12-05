package wicket.contrib.gmap3;

/**
 * Invented to replace @Deprecated in the gmap package.
 * @Deprecated produces many compiler warnings, this annotation doesn't.
 *
 * The annotation indicates that the annotated type, field, member was imported in our code base but
 * hasn't been fully reviewed or tested. Whenever using such a method or type make sure to check
 * if the code is actually doing what it is supposed to do.
 *
 * The gmap package contains partially old and obsolete, partially non-working code left over from
 * a not completed migration from gmap2 to gmap3.
 */
public @interface ReviewPending {

}
