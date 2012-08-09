package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.common.ui.inject.InjectorAwareObject;
import de.flower.rmt.model.db.type.activity.BlogUpdateMessage;
import de.flower.rmt.service.ILinkProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class BlogUpdateMessageRenderer extends InjectorAwareObject implements IMessageRenderer<BlogUpdateMessage> {

    @SpringBean(name = "wicketLinkProvider")
    protected ILinkProvider linkProvider;

    @Override
    public String toString(final BlogUpdateMessage message) {
        String resourceKey;
        if (message.isCommment()) {
            resourceKey = "activity.blog.comment.create";
        } else {
            resourceKey = "activity.blog.article.create";
        }
        Object[] params = new Object[]{getArticleLink(message)};
        String s = new StringResourceModel(resourceKey, Model.of(message), params).getObject();
        return s;
    }

    public String getArticleLink(BlogUpdateMessage message) {
        return linkProvider.deepLinkBlog(message.getArticleId());
    }

    @Override
    public boolean canHandle(final Object message) {
        return message instanceof BlogUpdateMessage;
    }
}
