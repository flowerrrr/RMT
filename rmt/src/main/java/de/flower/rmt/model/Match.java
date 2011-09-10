package de.flower.rmt.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author oblume
 */
@Entity
@DiscriminatorValue("Match")
public class Match extends Event {

    @ManyToOne
    // TODO: check if @OneToMany might be better
    private Team opponent;

//    @ManyToOne
//    private Jersey jersey;
}
