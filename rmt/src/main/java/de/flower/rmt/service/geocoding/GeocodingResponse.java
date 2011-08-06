package de.flower.rmt.service.geocoding;

import java.util.List;

/**
 * @author oblume
 */
public class GeocodingResponse {

    private String json;

    private String status;

    private List<GeocodingResult> results;



    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getStatus() {
        return status;
    }

    public List<GeocodingResult> getResultList() {
        return results;
    }

    public String toString() {
        return json;
    }
}
