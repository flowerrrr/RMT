package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import de.flower.common.util.geo.LatLng;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * @author flowerrrr
 */
@Entity
public class Club extends AbstractBaseEntity {

    @Column
    @NotBlank
    @Size(max = 50)
    private String name;

    @Column
    private Double lat;

    @Column
    private Double lng;

    protected Club() {
    }

    public Club(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        if (this.lat == null || this.lng == null) {
            return null;
        } else {
            return new LatLng(this.lat, this.lng);
        }
    }

    public void setLatLng(LatLng latLng) {
        if (latLng == null) {
            //noinspection AssignmentToNull
            this.lat = null;
            //noinspection AssignmentToNull
            this.lng = null;
        } else {
            this.lat = latLng.getLat();
            this.lng = latLng.getLng();
        }
    }

    @Override
    public String toString() {
        return "Club{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
