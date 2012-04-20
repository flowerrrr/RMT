package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Activity;
import de.flower.rmt.model.Activity_;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Invitation_;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.type.activity.EmailSentMessage;
import de.flower.rmt.model.type.activity.EventUpdateMessage;
import de.flower.rmt.model.type.activity.InvitationUpdateMessage;
import de.flower.rmt.repository.IActivityRepo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ActivityManager extends AbstractService implements IActivityManager {

    @Autowired
    private IActivityRepo activityRepo;

    @Autowired
    private IInvitationManager invitationManager;

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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void onCreateOrUpdate(final Event event, final boolean isNew) {
        Activity entity = newInstance();
        EventUpdateMessage message = new EventUpdateMessage(event);
        message.setCreated(isNew);
        message.setTeamName(event.getTeam().getName());
        message.setManagerName(entity.getUser().getFullname());
        entity.setMessage(message);
        save(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void onInvitationMailSent(final Event event) {
        Activity entity = newInstance();
        EmailSentMessage message = new EmailSentMessage(event);
        message.setManagerName(entity.getUser().getFullname());
        entity.setMessage(message);
        save(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void onInvitationUpdated(final Invitation invitation) {
        Invitation origInvitation = invitationManager.loadById(invitation.getId(), Invitation_.event, Invitation_.user);
        Check.isTrue(invitation != origInvitation);
        Activity entity = newInstance();
        InvitationUpdateMessage message = new InvitationUpdateMessage(origInvitation.getEvent());
        message.setUserName(origInvitation.getName());
        boolean changed = false;
        if (!entity.getUser().equals(origInvitation.getUser())) {
            // manager has updated invitation
            message.setManagerName(entity.getUser().getFullname());
            changed = true;
        }
        if (ObjectUtils.notEqual(invitation.getComment(), origInvitation.getComment())) {
            message.setComment(invitation.getComment());
            changed = true;
        }
        if (ObjectUtils.notEqual(invitation.getManagerComment(), origInvitation.getManagerComment())) {
            message.setManagerComment(invitation.getManagerComment());
            changed = true;
        }
        if (ObjectUtils.notEqual(invitation.getStatus(), origInvitation.getStatus())) {
            message.setStatus(invitation.getStatus());
            changed = true;
        }
        entity.setMessage(message);

        if (!changed) {
            // assert that implementation is correct.
            log.warn("No change detected for [" + invitation + "]");
        } else {
            save(entity);
        }
    }

    @Override
    public List<Activity> findLastN(final int num) {
        return activityRepo.findAll(new PageRequest(0, num, Sort.Direction.DESC, Activity_.date.getName())).getContent();
    }
}
