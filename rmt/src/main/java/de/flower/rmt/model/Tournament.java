package de.flower.rmt.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author oblume
 */
@Entity
@DiscriminatorValue("Tournament")
public class Tournament extends Event {

//    @ManyToOne
//    private Jersey jersey;
}
