package de.flower.rmt.ui.page.event;

import de.flower.common.ui.markup.html.basic.FallbackLabel;
import de.flower.common.ui.model.AbstractChainingModel;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.util.convert.AbstractConverter;
import de.flower.rmt.model.db.entity.Surface;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.markup.html.form.renderer.SurfaceRenderer;
import de.flower.rmt.ui.model.ModelFactory;
import de.flower.rmt.ui.model.VenueModel;
import de.flower.rmt.ui.page.venues.manager.VenueEditPage;
import de.flower.rmt.ui.page.venues.player.VenuePage;
import de.flower.rmt.util.Dates;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.*;
import org.apache.wicket.util.convert.IConverter;

import java.util.List;
import java.util.Locale;

/**
 * @author flowerrrr
 */
public class EventDetailsPanel extends BasePanel<Event> {

    public EventDetailsPanel(final IModel<? extends Event> model, final View view) {
        setDefaultModel(new CompoundPropertyModel<Object>(ModelFactory.eventModelWithAllAssociations(model.getObject())));

        add(new Label("team.name"));

        add(DateLabel.forDatePattern("date", Dates.DATE_MEDIUM_WITH_WEEKDAY));

        add(DateLabel.forDateStyle("dateTimeAsDate", "-S"));

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

        Link link = new Link<Venue>("venue") {
            @Override
            public void onClick() {
                IModel<Venue> model = new VenueModel(getModel());
                WebPage page = view == View.MANAGER ? new VenueEditPage(model) : new VenuePage(model);
                setResponsePage(page);
            }

            @Override
            public boolean isEnabled() {
                return getModel().getObject() != null;
            }
        };
        link.setBeforeDisabledLink("");
        link.add(new FallbackLabel("venue.name", new ResourceModel("venue.nullValid")).setEscapeModelStrings(false));
        add(link);

        add(new UniformLabel("uniform") {
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

    public static class UniformLabel extends Label {

        public UniformLabel(final String id) {
            super(id);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();
            setDefaultModel(new AbstractChainingModel<String, Uniform>((IModel<Uniform>) getDefaultModel()) {
                @Override
                public String getObject() {
                    if (getChainedModel().getObject() == null) {
                        return new ResourceModel("uniform.nullValid").getObject();
                    } else {
                        return new StringResourceModel("uniform.set", getChainedModel()).getObject();
                    }
                }

                @Override
                public void setObject(final String object) {
                    throw new UnsupportedOperationException("Feature not implemented!");
                }
            });
        }
    }

    public static class SurfaceListLabel extends Label {

        public SurfaceListLabel(final String id) {
            super(id);
        }

        @Override
        public <C> IConverter<C> getConverter(final Class<C> type) {
            return (IConverter<C>) new AbstractConverter<List<Surface>>() {

                @Override
                public String convertToString(final List<Surface> value, final Locale locale) {
                    return new SurfaceRenderer().renderList(value);
                }
            };
        }
    }
}
