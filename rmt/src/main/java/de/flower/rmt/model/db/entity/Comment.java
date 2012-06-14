package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author flowerrrr
 */
@Entity
public class Comment extends AbstractBaseEntity {

    public final static int MAXLENGTH = 255;

    @Column(length = MAXLENGTH)
    @Size(max = MAXLENGTH)
    private String text;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_invitation")
    private Invitation invitation;

    /**
     * FetchType EAGER needed as long as there's no commentManager that allows eager loading of comment instances.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @Index(name = "ix_author")
    private User author;

    protected Comment() {
    }

    public Comment(final String text, final Invitation invitation, final User author) {
        this.text = text;
        this.invitation = checkNotNull(invitation);
        this.author = checkNotNull(author);
    }


    public Comment(final Invitation invitation, final User author) {
        this(null, invitation, author);
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

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(final Invitation invitation) {
        this.invitation = invitation;
    }
}
