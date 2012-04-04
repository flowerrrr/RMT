package de.flower.common.ui.util;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.List;

/**
 * Helper class for dealing with wicket components.
 */
public final class ComponentUtils {

    private ComponentUtils() {
    }

    /**
     * Checks if a behavior is attached to any component in the current page.
     *
     * @param component the component
     * @param behaviorClass the behavior class
     * @return true, if behavior is found in page
     */
    public static boolean isBehaviorInPage(final Component component, final Class<? extends Behavior> behaviorClass) {
        final Page page = component.getPage();
        if (page.getBehaviors(behaviorClass).size() > 0) {
            return true;
        }
        final Boolean result = page.visitChildren(new IVisitor<Component, Boolean>() {

            @Override
            public void component(final Component component, final IVisit<Boolean> visit) {
                final List<? extends Behavior> behaviors = component.getBehaviors(behaviorClass);
                if (behaviors.size() > 0) {
                    visit.stop(true);
                }

            }

        });
        return result == null ? false : true;
    }

    /**
     * Gets the behavior.
     *
     * @param <T> the generic type
     * @param component the component
     * @param behaviorClass the behavior class
     * @return the first behavior or null if none is found.
     */
    public static <T extends Behavior> T getBehavior(final Component component, final Class<T> behaviorClass) {
        final List<T> list = component.getBehaviors(behaviorClass);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
