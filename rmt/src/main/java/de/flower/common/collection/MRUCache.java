package de.flower.common.collection;

import java.util.LinkedHashMap;
import java.util.Map;


public class MRUCache<K,V> extends LinkedHashMap<K,V> {

    private int maxSize;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public MRUCache(int maxSize) {
        super(maxSize, DEFAULT_LOAD_FACTOR, true);
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max Size must be greater than 0.");
        }
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
       return size() > maxSize;
    }



}
