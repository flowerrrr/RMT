package de.flower.common.ui.inject;

import org.apache.wicket.injection.Injector;

/**
 * @author flowerrrr
 */
public class InjectorAwareObject {

    public InjectorAwareObject() {
        Injector.get().inject(this);
    }
}
