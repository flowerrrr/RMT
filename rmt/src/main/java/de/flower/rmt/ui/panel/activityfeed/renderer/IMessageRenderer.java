package de.flower.rmt.ui.panel.activityfeed.renderer;

/**
 * @author flowerrrr
 */
public interface IMessageRenderer<T> {

    String toString(T message);

    boolean canHandle(Object message);

}
