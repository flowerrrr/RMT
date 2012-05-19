package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Activity;
import de.flower.rmt.model.db.entity.Activity_;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Invitation_;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.Event_;
import de.flower.rmt.model.db.type.activity.EmailSentMessage;
import de.flower.rmt.model.db.type.activity.EventUpdateMessage;
import de.flower.rmt.model.db.type.activity.InvitationUpdateMessage;
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

    @Autowired
    private IEventManager eventManager;

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
    public Activity loadById(final Long id) {
        return activityRepo.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void onCreateOrUpdate(Event event, final boolean isNew) {
        event = eventManager.loadById(event.getId(), Event_.team);
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
        // do not track when comments are removed. what to display then?
        if (invitation.getComment() != null && ObjectUtils.notEqual(invitation.getComment(), origInvitation.getComment())) {
            message.setComment(invitation.getComment());
            changed = true;
        }
        if (invitation.getManagerComment() != null && ObjectUtils.notEqual(invitation.getManagerComment(), origInvitation.getManagerComment())) {
            message.setManagerComment(invitation.getManagerComment());
            changed = true;
        }
        if (ObjectUtils.notEqual(invitation.getStatus(), origInvitation.getStatus())) {
            message.setStatus(invitation.getStatus());
            changed = true;
        }
        entity.setMessage(message);

        if (changed) {
            save(entity);
        }
    }


    @Override
    public List<Activity> findLastN(final int page, final int size) {
        return activityRepo.findAll(new PageRequest(page, size, Sort.Direction.DESC, Activity_.date.getName())).getContent();
    }
}
