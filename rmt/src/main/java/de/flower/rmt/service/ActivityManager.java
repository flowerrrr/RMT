package de.flower.rmt.service;

import de.flower.rmt.model.Activity;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ActivityManager extends AbstractService implements IActivityManager {

    @Autowired
    private IActivityRepo activityRepo;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Activity entity) {
        validate(entity);
        activityRepo.save(entity);
    }

    private Activity newInstance() {
        Activity entity = new Activity(getClub());
        entity.setUser(securityService.getUser());
        entity.setDate(new Date());
        return entity;
    }

    @Override
    public void onCreateOrUpdate(final Event event, final boolean isNew) {
        Activity entity = newInstance();
        entity.setMessage(isNew ? "Event created" : "Event updated");
        save(entity);
    }

    @Override
    public void onInvitationMailSent(final Event event) {
        Activity entity = newInstance();
        entity.setMessage("Invitation mail was sent");
        save(entity);
    }

    @Override
    public void onInvitationUpdated(final Invitation invitation, final Invitation origInvitation) {
        Activity entity = newInstance();
        entity.setMessage("Invitation was updated");
        save(entity);
    }
}
