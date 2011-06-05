package de.flower.rmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author oblume
 */
@Entity
public class MyTeamBE extends AbstractBaseEntity {

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
