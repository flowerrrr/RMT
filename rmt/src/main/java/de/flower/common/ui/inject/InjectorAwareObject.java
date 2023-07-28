package de.flower.common.ui.inject;

import org.apache.wicket.injection.Injector;


public class InjectorAwareObject {

    public InjectorAwareObject() {
        Injector.get().inject(this);
    }
}
