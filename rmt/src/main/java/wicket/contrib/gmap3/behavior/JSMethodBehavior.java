package wicket.contrib.gmap3.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import wicket.contrib.gmap3.ReviewPending;

/**
 * The Class JSMethodBehavior.
 */
@ReviewPending
// remove when class is tested
abstract class JSMethodBehavior extends Behavior {

    private static final long serialVersionUID = 1L;

    private final String _attribute;

    /**
     * Instantiates a new jS method behavior.
     *
     * @param attribute the attribute
     */
    public JSMethodBehavior(final String attribute) {
        _attribute = attribute;
    }

    /**
     * On component tag.
     *
     * @param component the component
     * @param tag the tag
     * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component,
     * org.apache.wicket.markup.ComponentTag)
     */
    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        String invoke = getJSinvoke();

        if (_attribute.equalsIgnoreCase("href")) {
            invoke = "javascript:" + invoke;
        }

        tag.put(_attribute, invoke);
    }

    protected abstract String getJSinvoke();
}