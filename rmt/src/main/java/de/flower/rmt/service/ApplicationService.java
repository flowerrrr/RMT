package de.flower.rmt.service;

import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Service for everything that does not fit somewhere else.
 *
 * @author flowerrrr
 */
@Service
public class ApplicationService extends AbstractService implements IApplicationService {

    @Value("${resource.motd}")
    private Resource motdResource;

    /**
     * Loads motd.txt from file system and returns content.
     * Execution time about 1 ms!
     *
     * @return
     */
    @Override
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
}
