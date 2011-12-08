package de.flower.rmt.test;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Duplicates WicketTesterHelper#getComponentData but avoids reading out model values.
 * Using the original version of the method inside unit tests might lead to
 * error logs and even test failure cause model-objects are messed up.
 *
 * @author flowerrrr
 */
public class RMTWicketTesterHelper {

    /**
     * Gets recursively all <code>Component</code>s of a given <code>Page</code>, extracts the
     * information relevant to us, and adds them to a <code>List</code>.
     *
     * @param page the <code>Page</code> to analyze
     * @param readModelValues if true the model values of the components are accessed.
     *          WARNING: might lead to unexpected sideeffects. Method should only be called at end of unit tests
     *          when this flag is set.
     * @return a <code>List</code> of <code>Component</code> data objects
     */
    public static List<WicketTesterHelper.ComponentData> getComponentData(final Page page, final boolean readModelValues) {
        final List<WicketTesterHelper.ComponentData> data = new ArrayList<WicketTesterHelper.ComponentData>();

        if (page != null) {
            page.visitChildren(new IVisitor<Component, Void>() {
                public void component(final Component component, final IVisit<Void> visit) {
                    final WicketTesterHelper.ComponentData object = new WicketTesterHelper.ComponentData();

                    // anonymous class? Get the parent's class name
                    String name = component.getClass().getName();
                    if (name.indexOf("$") > 0) {
                        name = component.getClass().getSuperclass().getName();
                    }

                    // remove the path component
                    name = Strings.lastPathComponent(name, Component.PATH_SEPARATOR);

                    object.path = component.getPageRelativePath();
                    object.type = name;
                    if (readModelValues) {
                        try
                        {
                            object.value = component.getDefaultModelObjectAsString();
                        }
                        catch (Exception e)
                        {
                            object.value = e.getMessage();
                        }
                    } else {
                        object.value = "<not read>";
                    }
                    data.add(object);
                }
            });
        }
        return data;
    }
}
