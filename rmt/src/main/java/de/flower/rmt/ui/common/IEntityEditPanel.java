package de.flower.rmt.ui.common;

import de.flower.common.model.IEntity;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public interface IEntityEditPanel<T extends IEntity> {

    void init(IModel<T> model);

}
