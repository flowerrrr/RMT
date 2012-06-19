package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.repository.ICalItemRepo;
import de.flower.rmt.service.security.ISecurityService;
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
public class CalendarManager extends AbstractService implements ICalendarManager {

    @Autowired
    private ICalItemRepo calItemRepo;

    @Autowired
    private ISecurityService securityService;

    @Override
    public CalItem newInstance() {
        CalItem entity = new CalItem();
        entity.setUser(securityService.getUser());
        return entity;
    }

    @Override
    public CalItem loadById(final Long id) {
        CalItem entity = calItemRepo.findOne(id);
        Check.notNull(entity, "CalItem [" + id + "] not found");
        return entity;
    }

    @Override
    public void save(final CalItem entity) {
        validate(entity);
        calItemRepo.save(entity);
    }

    @Override
    public List<CalItem> findAllByUser(final User user) {
        return calItemRepo.findAllByUser(user);
    }
}
