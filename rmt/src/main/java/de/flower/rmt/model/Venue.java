package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.common.util.geo.LatLngEx;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author oblume
 */
@Entity
public class Venue extends AbstractBaseEntity {

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

    @NotNull
    @ManyToOne
    private Club club;

    public Venue() {
    }

    public Venue(Club club) {
        this.club = club;
    }

    public Venue(String name, String address, LatLngEx gLatLng, Club club) {
        this.name = name;
        this.address = address;
        setLatLng(gLatLng);
        this.club = club;
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

    public LatLngEx getLatLng() {
        if (this.lat == null || this.lng == null) {
            return null;
        } else {
            return new LatLngEx(this.lat, this.lng);
        }
    }

    public void setLatLng(LatLngEx latLng) {
        this.lat = latLng.getLat();
        this.lng = latLng.getLng();
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
