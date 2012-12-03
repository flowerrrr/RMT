package de.flower.rmt.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.ui.ajax.dragndrop.DraggableDto;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Invitation_;
import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.QLineup;
import de.flower.rmt.model.db.entity.QLineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.repository.ILineupItemRepo;
import de.flower.rmt.repository.ILineupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LineupManager extends AbstractService implements ILineupManager {

    @Autowired
    private ILineupRepo lineupRepo;

    @Autowired
    private ILineupItemRepo lineupItemRepo;

    @Autowired
    private IInvitationManager invitationManager;

    @Autowired
    private IActivityManager activityManager;

    @Override
    public Lineup findLineup(final Event event) {
        BooleanExpression isEvent = QLineup.lineup.event.eq(event);
        return lineupRepo.findOne(isEvent);
    }

    @Override
    public Lineup findOrCreateLineup(final Event event, Path<?>... attributes) {
        Lineup lineup = findLineup(event);
        if (lineup == null) {
            lineup = createLineup(event);
        }
        return lineup;
    }

    @Override
    public List<LineupItem> findLineupItems(final Event event, Path<?>... attributes) {
        Lineup lineup = findOrCreateLineup(event);
        BooleanExpression isEvent = QLineupItem.lineupItem.lineup.event.eq(event);
        List<LineupItem> items = lineupItemRepo.findAll(isEvent, attributes);
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].equals(QLineupItem.lineupItem.invitation)) {
                // load invitations with users as we need them later. fetching user name via attributes is not possible
                // once loaded into session we avoid LIE
                invitationManager.findAllByEvent(event, Invitation_.user);
            }
        }
        return items;
    }

    @Override
    public List<Invitation> findInvitationsInLinuep(final Event event) {
        List<LineupItem> lineupItems = findLineupItems(event, QLineupItem.lineupItem.invitation);
        return Lists.transform(lineupItems, new Function<LineupItem, Invitation>() {
            @Override
            public Invitation apply(final LineupItem input) {
                return input.getInvitation();
            }
        });
    }

    @Override
    public Lineup createLineup(final Event event) {
        Lineup lineup = new Lineup(event);
        lineupRepo.save(lineup);
        return lineup;
    }

    @Override
    public void drop(final DraggableDto dto) {
        Invitation invitation = invitationManager.loadById(dto.entityId, Invitation_.event);
        Lineup lineup = findOrCreateLineup(invitation.getEvent());
        Check.notNull(lineup);
        LineupItem item = lineupItemRepo.findByLineupAndInvitation(lineup, invitation);
        if (item == null) {
            item = new LineupItem(lineup, invitation);
        }
        // update positions
        item.setTop(dto.top);
        item.setLeft(dto.left);
        save(item);
    }

    @Override
    public void removeLineupItem(final Long invitationId) {
        Invitation invitation = invitationManager.loadById(invitationId);
        Lineup lineup = findLineup(invitation.getEvent());
        if (lineup != null) {
            LineupItem item = lineupItemRepo.findByLineupAndInvitation(lineup, invitation);
            if (item != null) {
                lineupItemRepo.delete(item);
            }
        }
    }

    @VisibleForTesting
    @Override
    public void save(final LineupItem item) {
        validate(item);
        lineupItemRepo.save(item);
    }

    @Override
    public void delete(final Long lineupId) {
        lineupRepo.delete(lineupId); // delete is cascaded to lineupitems
    }

    @Override
    public void publishLineup(final Event event) {
        Lineup lineup = findOrCreateLineup(event);
        Check.notNull(lineup);
        lineup.setPublished(true);
        lineupRepo.save(lineup);
        activityManager.onLineupPublished(lineup);
    }
}
