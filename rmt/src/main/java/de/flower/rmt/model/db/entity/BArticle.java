package de.flower.rmt.model.db.entity;

import de.flower.rmt.model.db.entity.event.Event;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * blog article.
 *
 * @author flowerrrr
 */
@Entity
public class BArticle extends AbstractClubRelatedEntity {

    public final static int HEADING_MAXLENGTH = 80;

    @Column(length = HEADING_MAXLENGTH)
    @Size(max = HEADING_MAXLENGTH)
    @NotBlank(message = "{validation.blog.article.heading}")
    private String heading;

    @Lob
    @NotBlank(message = "{validation.blog.article.text}")
    private String text;

    /**
     * FetchType EAGER needed as long as there's no bCommentManager that allows eager loading of comment instances.
     */
    @NotNull
    @ManyToOne
    @Index(name = "ix_author")
    private User author;

    /**
     * Optional link to event.
     */
    @ManyToOne
    @Index(name = "ix_event")
    private Event event;

    protected BArticle() {
    }

    public BArticle(User author, final Club club) {
        super(club);
        this.author = author;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(final String heading) {
        this.heading = heading;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }
}
