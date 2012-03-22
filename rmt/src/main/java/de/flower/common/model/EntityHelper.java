package de.flower.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author flowerrrr
 */
public class EntityHelper {

    public static List<Long> convertEntityListToIdList(Collection<? extends IEntity> entities) {
        List<Long> idList = new ArrayList<Long>();
        for (IEntity entity : entities) {
            idList.add(entity.getId());
        }
        return idList;
    }
}
