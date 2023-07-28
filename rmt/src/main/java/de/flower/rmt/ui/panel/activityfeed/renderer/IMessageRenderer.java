package de.flower.rmt.ui.panel.activityfeed.renderer;


public interface IMessageRenderer<T> {

    String toString(T message);

    boolean canHandle(Object message);

}
