package de.flower.common.model;

import de.flower.common.model.db.entity.IEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class EntityHelper {

    public static List<Long> convertEntityListToIdList(Collection<? extends IEntity> entities) {
        List<Long> idList = new ArrayList<Long>();
        for (IEntity entity : entities) {
            idList.add(entity.getId());
        }
        return idList;
    }
}
