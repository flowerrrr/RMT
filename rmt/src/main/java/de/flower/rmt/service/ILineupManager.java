package de.flower.rmt.service;

import com.google.common.annotations.VisibleForTesting;
import com.mysema.query.types.Path;
import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.LineupItemDto;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface ILineupManager {

    Lineup findLineup(Event event, Path<?>... attributes);

    List<LineupItem> findLineupItems(Event object, Path<?>... attributes);

    Lineup createLineup(final Event event);

    void drop(LineupItemDto dto);

    void removeLineupItem(Long invitationId);

    @Deprecated
    @VisibleForTesting
    void save(LineupItem item);

    void delete(Long lineupId);

    void publishLineup(Event event);
}
