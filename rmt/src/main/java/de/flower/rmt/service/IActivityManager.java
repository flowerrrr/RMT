package de.flower.rmt.service;

import de.flower.rmt.model.Activity;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IActivityManager {

    //    void save(Activity entity);
//
    Activity loadById(Long id);
//
//    List<Activity> findAll();
//
//    void delete(Long id);

    void onCreateOrUpdate(Event event, boolean isNew);

    void onInvitationMailSent(Event event);

    /**
     * Method must be called before invitation is saved.
     *
     * @param invitation
     */
    void onInvitationUpdated(Invitation invitation);

    List<Activity> findLastN(int num, final int i);
}
