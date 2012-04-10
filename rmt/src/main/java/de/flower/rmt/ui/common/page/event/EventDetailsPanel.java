package de.flower.rmt.ui.common.page.event;

import de.flower.common.ui.markup.html.basic.FallbackLabel;
import de.flower.rmt.model.Surface;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.ModelFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.convert.IConverter;

import java.util.List;
import java.util.Locale;

/**
 * @author flowerrrr
 */
public class EventDetailsPanel extends BasePanel<Event> {

    public EventDetailsPanel(final IModel<Event> model) {
        setDefaultModel(new CompoundPropertyModel<Object>(ModelFactory.eventModelWithAllAssociations(model.getObject())));

        add(new Label("team.name"));

        add(DateLabel.forDateStyle("date", "S-"));

        add(DateLabel.forDateStyle("timeAsDate", "-S"));

        add(new WebMarkupContainer("kickoffContainer") {
            {
                add(DateLabel.forDateStyle("kickoffAsDate", "-S"));
            }

            @Override
            public boolean isVisible() {
                return EventType.isSoccerEvent(model.getObject());
            }
        });

        add(new Label("type", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return new ResourceModel(EventType.from(model.getObject()).getResourceKey()).getObject();
            }
        }));

        add(new FallbackLabel("opponent.name", new ResourceModel("opponent.nullValid")) {
            @Override
            public boolean isVisible() {
                return EventType.isMatch(model.getObject());
            }
        }.setEscapeModelStrings(false));

        add(new FallbackLabel("venue.name", new ResourceModel("venue.nullValid")).setEscapeModelStrings(false));

        add(new FallbackLabel("uniform.name", new ResourceModel("uniform.nullValid")) {
            @Override
            public boolean isVisible() {
                return EventType.isSoccerEvent(model.getObject());
            }
        }.setEscapeModelStrings(false));

        add(new SurfaceListLabel("surfaceList") {
            @Override
            public boolean isVisible() {
                return EventType.isSoccerEvent(model.getObject());
            }
        });

        add(new Label("summary"));

        add(new Label("comment") {
            @Override
            public boolean isVisible() {
                return StringUtils.isNotBlank(getDefaultModelObjectAsString());
            }
        });
    }

    public static class SurfaceListLabel extends Label {

        public SurfaceListLabel(final String id) {
            super(id);
        }

        @Override
        public <C> IConverter<C> getConverter(final Class<C> type) {
            return (IConverter<C>) new IConverter<List<Surface>>() {
                @Override
                public List<Surface> convertToObject(final String value, final Locale locale) {
                    throw new UnsupportedOperationException("Feature not implemented!");
                }

                @Override
                public String convertToString(final List<Surface> value, final Locale locale) {
                    return Surface.render(value);
                }
            };
        }
    }
}
