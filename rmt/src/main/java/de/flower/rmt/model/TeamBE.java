package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author oblume
 */
@Entity
public class TeamBE extends AbstractBaseEntity {

    @NotBlank @Size(max = 40)
    @Column
    private String name;

    @URL @Size(max = 255)
    @Column
    private String url;

    @NotNull
    @ManyToOne
    private ClubBE clubBE;

//    @ManyToMany
//    private Set<Users> managers;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
