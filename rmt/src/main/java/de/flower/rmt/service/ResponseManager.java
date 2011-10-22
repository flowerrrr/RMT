package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.*;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IResponseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ResponseManager extends AbstractService implements IResponseManager {

    @Autowired
    private IResponseRepo responseRepo;

    @Autowired
    private IPlayerManager playerManager;

    @Override
    public List<Response> findByEventAndStatus(Event event, RSVPStatus rsvpStatus) {
        return responseRepo.findByEventAndStatusOrderByDateAsc(event, rsvpStatus);
    }

    @Override
    public Response findByEventAndUser(Event event, User user) {
        // find player instance of this user belonging to the events team
        Player player = playerManager.findByTeamAndUser(event.getTeam(), user);
        Check.notNull(player, "User [{}] not associated to event [{}]", user.getId(), event.getTeam().getId());
        return responseRepo.findByEventAndPlayer(event, player);
    }

    @Override
    @Transactional(readOnly = false)
    public Response respond(Event event, Player player, RSVPStatus status, String comment) {
        // do some checking if responding to event is allowed
        Response response = new Response(player, event);
        response.setStatus(status);
        Comment c = new Comment(comment, player.getUser(), response);
        return responseRepo.save(response);
    }



}
