package de.flower.rmt.service.geocoding;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import static de.flower.common.util.Check.*;

/**
 * @author flowerrrr
 */
@Service
public class GoogleGeocodingService implements IGeocodingService {

    private final static Logger log = LoggerFactory.getLogger(GoogleGeocodingService.class);

    private final static String format = "json";

    @Value("${geocoding.default.region}")
    private String defaultRegion;

    @Value("${geocoding.default.language}")
    private String defaultLanguage;

    @Override
    public List<GeocodingResult> geocode(String address) {
        return geocode(address, defaultRegion, defaultLanguage);
    }

    public List<GeocodingResult> geocode(String address, String region, String language) {

        notBlank(address);
        notBlank(region);
        notBlank(language);

        log.info("Geocoding for [" + address + "]");

        HttpGet httpGet = null;

        try {
            String params = "address=" + URLEncoder.encode(address, HTTP.UTF_8);
            // NOTE (flowerrrr - 05.08.11): use region of currently logged in user.
            params += "&region=" + region;
            params += "&language=" + language;
            params += "&sensor=false";

            HttpClient httpClient = new DefaultHttpClient();
            httpGet = new HttpGet("http://maps.googleapis.com/maps/api/geocode/" + format + "?" + params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String response = EntityUtils.toString(httpResponse.getEntity());
            Gson gson = new Gson();
            GeocodingResponse gr = gson.fromJson(response, GeocodingResponse.class);
            gr.setJson(response);
            if ("INVALID_REQUEST".equals(gr.getStatus())) {
                throw new RuntimeException("Invalid request [" + httpGet.getURI() + "]");
            } else if ("ZERO_RESULTS".equals(gr.getStatus())) {
                gr.getResultList();
            } else if (!"OK".equals(gr.getStatus())) {
                throw new RuntimeException("Invalid geocode response: " + gr.toString());
            }
            log.debug("Geocode result for [" + address + "]: " + gr.getJson());
            return gr.getResultList();
        } catch (IOException e) {
            log.error("Error issuing geocode request [" + (httpGet != null ? httpGet.getURI() : "") + "]", e);
            throw new RuntimeException(e);
        }


    }

}
