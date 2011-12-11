package de.flower.common.test.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.ContainerInfo;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.markup.MarkupResourceStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.string.Strings;
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
public class WicketTesterHelper {

    public static class ComponentData extends org.apache.wicket.util.tester.WicketTesterHelper.ComponentData {

        public Component component;
    }

    /**
     * Gets recursively all <code>Component</code>s of a given <code>Page</code>, extracts the
     * information relevant to us, and adds them to a <code>List</code>.
     *
     * @param page            the <code>Page</code> to analyze
     * @param readModelValues if true the model values of the components are accessed.
     *                        WARNING: might lead to unexpected sideeffects. Method should only be called at end of unit tests
     *                        when this flag is set.
     * @return a <code>List</code> of <code>Component</code> data objects
     */
    public static List<ComponentData> getComponentData(final Page page, final boolean readModelValues) {
        final List<ComponentData> data = new ArrayList<ComponentData>();

        if (page != null) {
            page.visitChildren(new IVisitor<Component, Void>() {
                public void component(final Component component, final IVisit<Void> visit) {
                    final ComponentData object = new ComponentData();

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
                        try {
                            object.value = component.getDefaultModelObjectAsString();
                        } catch (Exception e) {
                            object.value = e.getMessage();
                        }
                    } else {
                        object.value = "<not read>";
                    }
                    object.component = component;
                    data.add(object);
                }
            });
        }
        return data;
    }

    /**
     * Create markup based on a string.
     *
     * Replacement for Markup.of(string). Markup.of(string) has issues with header contribution,
     * the markup parser does not add HtmlHeaderSectionHandler to the parsers filter list.
     *
     * @param html
     * @param testPage
     * @return
     */
    public static Markup creatPageMarkup(final String html, final WebPage page) {

        final ContainerInfo containerInfo = new ContainerInfo(page);
        MarkupResourceStream markupResourceStream = new MarkupResourceStream(
                new StringResourceStream(html), containerInfo, page.getClass());
        MarkupParser markupParser = page.getApplication().getMarkupSettings()
            .getMarkupFactory()
            .newMarkupParser(markupResourceStream);
        try {
            Markup markup = markupParser.parse();
            return markup;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
