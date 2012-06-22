package de.flower.common.ui.util;

import de.flower.common.util.Check;
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
     * @param component     the component
     * @param behaviorClass the behavior class
     * @return true, if behavior is found in page
     */
    public static boolean isBehaviorInPage(final Component component, final Class<? extends Behavior> behaviorClass) {
        return getBehaviorInPage(component, behaviorClass) != null;
    }

    public static Behavior getBehaviorInPage(final Component component, final Class<? extends Behavior> behaviorClass) {
        final Page page = component.getPage();
        List<? extends Behavior> behaviors = page.getBehaviors(behaviorClass);
        if (behaviors.size() > 0) {
            Check.isTrue(behaviors.size() == 1);
            return behaviors.get(0);
        }
        final Behavior behavior = page.visitChildren(new IVisitor<Component, Behavior>() {

            @Override
            public void component(final Component component, final IVisit<Behavior> visit) {
                final List<? extends Behavior> behaviors = component.getBehaviors(behaviorClass);
                if (behaviors.size() > 0) {
                    Check.isTrue(behaviors.size() == 1);
                    visit.stop(behaviors.get(0));
                }
            }
        });
        return behavior;
    }

    /**
     * Gets the behavior.
     *
     * @param <T>           the generic type
     * @param component     the component
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
