package de.flower.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author flowerrrr
 */
public class StopWatch {

    private final static Logger log = LoggerFactory.getLogger(StopWatch.class);

    public static <T> T execute(Callable<T> callable) {
        long start = System.nanoTime();
        T result;
        try {
            result = callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long end = System.nanoTime();
        log.info("Execution time: " + (end - start) / (1000 * 1000) + " ms");
        return result;
    }

}
