package de.flower.rmt.model.db.entity;

import de.flower.common.util.geo.LatLng;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name = "venue")
public class Venue extends AbstractClubRelatedEntity {

    @NotBlank
    @Size(max = 80)
    @Column
    private String name;

    @Size(max = 255)
    @Column
    private String address;

    @Column
    private Double lat;

    @Column
    private Double lng;

    protected Venue() {
    }

    public Venue(Club club) {
        super(club);
    }

    public Venue(String name, String address, LatLng gLatLng, Club club) {
        super(club);
        this.name = name;
        this.address = address;
        setLatLng(gLatLng);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

}
