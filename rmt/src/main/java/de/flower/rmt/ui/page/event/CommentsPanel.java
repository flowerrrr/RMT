package de.flower.rmt.ui.page.event;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxEditableMultiLineLabelExtended;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.markup.html.form.TextAreaMaxLengthBehavior;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Comment;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.security.SecurityService;
import de.flower.rmt.service.CommentManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
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


public class CommentsPanel extends BasePanel<Invitation> {

    @SpringBean
    private CommentManager commentManager;

    @SpringBean
    private SecurityService securityService;

    private boolean addNew;

    public CommentsPanel(final IModel<Invitation> model) {
        super(model);

        add(new AjaxEventListener(model));

        IModel<List<Comment>> listModel = new LoadableDetachableModel<List<Comment>>() {

            @Override
            protected List<Comment> load() {
                List<Comment> list = model.getObject().getComments();
                if (addNew) {
                    // append transient object
                    Comment comment = commentManager.newInstance(model.getObject());
                    list.add(comment);
                }
                return list;
            }
        };

        ListView<Comment> comments = new ListView<Comment>("comments", listModel) {
            @Override
            protected void populateItem(final ListItem<Comment> item) {
                AjaxEditableMultiLineLabel<String> editLabel = new AjaxEditableMultiLineLabelExtended<String>("text", new PropertyModel<String>(item.getModel(), "text")) {
                    {
                        setRows(3);
                        getEditor().add(new TextAreaMaxLengthBehavior(Comment.MAXLENGTH));
                        getEditor().add(AttributeModifier.replace("title", new ResourceModel("invitation.comments.edit.help")));
                    }

                    @Override
                    protected void onSubmit(final AjaxRequestTarget target) {
                        super.onSubmit(target);
                        Comment comment = item.getModelObject();
                        if (StringUtils.isBlank(comment.getText())) {
                            if (!comment.isNew()) commentManager.remove(comment);
                            // update this list to remove line
                            AjaxEventSender.send(this, model);
                        } else {
                            commentManager.save(item.getModelObject());
                        }
                        addNew = false;
                    }

                    @Override
                    public boolean isVisible() {
                        return securityService.isCurrentUser(item.getModelObject().getAuthor());
                    }

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        if (item.getModelObject().isNew() && AjaxRequestTarget.get() != null) {
                            // this is a new comment created after clicking the add-new-comment icon.
                            // put label in edit mode.
                            getLabel().setVisible(false);
                            getEditor().setVisible(true);
                            AjaxRequestTarget.get().focusComponent(getEditor());
                        }
                    }
                };
                item.add(editLabel);

                item.add(new Label("readonlyText", new PropertyModel<String>(item.getModel(), "text")) {
                    @Override
                    public boolean isVisible() {
                        return !securityService.isCurrentUser(item.getModelObject().getAuthor());
                    }
                });

                item.add(new Label("author", item.getModelObject().getAuthor().getFullname()) {
                    {
                        add(AttributeAppender.append("title", item.getModelObject().getAuthor().getFullname()));
                    }

                    @Override
                    public boolean isVisible() {
                        // hide author for invitation player
                        return !item.getModelObject().getAuthor().equals(model.getObject().getUser());
                    }
                });
            }
        };
        add(comments);

        add(new AjaxLink("addButton", model) {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                addNew = true;
                AjaxEventSender.send(this, model);
            }
        });
    }
}
