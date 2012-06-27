package de.flower.rmt.ui.panel;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IICalendarProvider;
import de.flower.rmt.service.mail.ICalendarHelper;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.nio.charset.Charset;

/**
 * @author flowerrrr
 */
public class DownloadIcsLink extends ResourceLink {

    public DownloadIcsLink(final String id, final IModel<Event> model, String filename) {
        super(id, new ICalendarResource(model, filename));
    }

    public static class ICalendarResource extends ByteArrayResource {

        @SpringBean
        private IICalendarProvider iCalendarProvider;

        private IModel<Event> model;

        public ICalendarResource(final IModel<Event> model, final String filename) {
            super(ICalendarHelper.CONTENT_TYPE_HTTP, null, filename);
            this.model = model;
            Injector.get().inject(this);
        }

        @Override
        protected byte[] getData(final Attributes attributes) {
            System.out.println(Charset.defaultCharset().name());
            return ICalendarHelper.getBytes(iCalendarProvider.getICalendar(model.getObject()));
        }
    }
}
