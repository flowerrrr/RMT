package de.flower.common.test.mock;

import ch.qos.logback.core.Appender;

import java.util.List;

/**
 * @author flowerrrr
 */

public interface IListAppender<T> extends Appender<T> {

    List<T> getList();

    void reset();
}