package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.service.ICalendarManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class CalItemModel extends AbstractEntityModel<CalItem> {

    @SpringBean
    private ICalendarManager manager;

    public CalItemModel(CalItem entity) {
        super(entity);
    }

    public CalItemModel() {
        super();
    }

    public CalItemModel(final IModel<CalItem> model) {
        super(model);
    }

    @Override
    protected CalItem load(Long id) {
        return manager.loadById(id);
    }

}
