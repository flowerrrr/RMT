package de.flower.rmt.service;

import com.google.common.io.Files;
import de.flower.rmt.model.db.entity.Property;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.repository.IPropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Service for everything that does not fit somewhere else.
 */
@Service
public class ApplicationService extends AbstractService {

    @Value("${resource.motd}")
    private Resource motdResource;

    @Autowired
    private IPropertyRepo propertyRepo;

    /**
     * Loads motd.txt from file system and returns content.
     * Execution time about 1 ms!
     *
     * @return
     */
    public String getMessageOfTheDay() {
        // resource.exists also returns true when resource was loaded and is later removed from file system. so
        // need to check if file actually exist.
        if (!motdResource.exists()) {
            return null;
        } else {
            try {
                File file = motdResource.getFile();
                if (file.exists()) {
                    return Files.toString(motdResource.getFile(), Charset.defaultCharset());
                } else {
                    return null;
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return e.getMessage();
            }
        }
    }

    /**
     * tries to find club-property.
     * @param name
     * @return null, if no property is found.
     */
    public String getProperty(String name) {
        Property property = propertyRepo.findByClubAndName(getClub(), name);
        return property == null ? null : property.getValue();
    }

    public String getUserProperty(User user, String name) {
        Property property = propertyRepo.findByUserAndName(user, name);
        return property == null ? null : property.getValue();
    }

    public void saveUserProperty(User user, String name, String value) {
        Property property = propertyRepo.findByUserAndName(user, name);
        if (property == null) {
            property = new Property(user);
            property.setName(name);
            property.setValue(value);
        } else {
            property.setValue(value);
        }
        propertyRepo.save(property);
    }

}
