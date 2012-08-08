package de.flower.rmt.ui.page.blog;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxEditableMultiLineLabelExtended;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.markup.html.form.TextAreaMaxLengthBehavior;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.model.db.entity.BComment;
import de.flower.rmt.model.db.entity.QBComment;
import de.flower.rmt.service.IBlogManager;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.util.Dates;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class BCommentsPanel extends RMTBasePanel {

    @SpringBean
    private IBlogManager blogManager;

    public BCommentsPanel(final IModel<BArticle> model) {
        super(model);

        add(new AjaxEventListener(model, BComment.class));

        add(new ArticleListPanel.NumCommentsLabel("numComments", model));

        ListView<BComment> comments = new ListView<BComment>("comments", getListModel(model)) {
            @Override
            protected void populateItem(final ListItem<BComment> item) {
                BComment comment = item.getModelObject();
                item.add(new Label("author", comment.getAuthor().getFullname()));
                item.add(new Label("date", Dates.formatDateTimeShortWithWeekday(comment.getCreateDate())));
                AjaxEditableMultiLineLabel<String> editLabel = new AjaxEditableMultiLineLabelExtended("text", new PropertyModel<String>(item.getModel(), "text")) {
                    {
                        setRows(7);
                        if (isEnabled()) {
                            getLabel().add(AttributeModifier.replace("title", new ResourceModel("blog.comments.label.help")));
                        }
                        getEditor().add(new TextAreaMaxLengthBehavior(BComment.MAXLENGTH));
                        getEditor().add(AttributeModifier.replace("title", new ResourceModel("blog.comments.edit.help")));
                    }

                    @Override
                    protected void onSubmit(final AjaxRequestTarget target) {
                        super.onSubmit(target);
                        BComment comment = item.getModelObject();
                        if (StringUtils.isBlank(comment.getText())) {
                            blogManager.remove(comment);
                            // update this list to remove comment
                            AjaxEventSender.send(this, model);
                        } else {
                            blogManager.save(comment);
                        }
                    }

                    @Override
                    public boolean isEnabled() {
                        return securityService.isCurrentUserOrManager(item.getModelObject().getAuthor());
                    }
                };
                item.add(editLabel);
                item.add(new AjaxLinkWithConfirmation("removeLink", new ResourceModel("blog.comment.delete.confirm")) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        blogManager.remove(item.getModelObject());
                        // update this list to remove comment
                        AjaxEventSender.send(this, model);
                    }

                    @Override
                    public boolean isVisible() {
                        return securityService.isCurrentUserOrManager(item.getModelObject().getAuthor());
                    }
                });
            }
        };
        add(comments);

        // new comment form
        add(new CommentFormPanel(model));
    }

    private IModel<List<BComment>> getListModel(final IModel<BArticle> model) {
        return new LoadableDetachableModel<List<BComment>>() {
            @Override
            protected List<BComment> load() {
                return blogManager.findAllComments(model.getObject(), QBComment.bComment.author);
            }
        };
    }
}
