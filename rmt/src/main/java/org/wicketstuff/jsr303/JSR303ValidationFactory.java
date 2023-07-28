/**
 *
 */
package org.wicketstuff.jsr303;

import de.flower.common.annotation.Patched;

import javax.annotation.PostConstruct;
import javax.validation.*;

/**
 * Patched version to use cached validator factory instead of re-creating validator factory for every #getValidator call.
 *
 * @author flowerrrr
 */

@Patched
public class JSR303ValidationFactory {

    private ValidatorFactory validatorFactory;

    private DefaultViolationMessageRenderer violationMessageRenderer;

    public Validator getValidator() {
        return validatorFactory.getValidator();
    }

    public DefaultViolationMessageRenderer getViolationMessageRenderer() {
        return violationMessageRenderer;
    }

    @PostConstruct
    public void init() {

         final Configuration<?> configuration = Validation.byDefaultProvider().configure();
        // FIXME seems like needed for hib-val 4.0.2.? strange enough it does
        // not respect the locale passed on interpolate call. Working on it.

        // geez. they screwed it up.
        // http://opensource.atlassian.com/projects/hibernate/browse/HV-306
        // fixed in 4.1.0.beta2 ... Locale.setDefault(Session.get().getLocale());

        MessageInterpolator targetInterpolator = configuration.getDefaultMessageInterpolator();
        configuration.messageInterpolator(new WicketSessionLocaleMessageInterpolator(targetInterpolator));

        if (violationMessageRenderer == null) {
            violationMessageRenderer = new DefaultViolationMessageRenderer();
        }

        validatorFactory = configuration.buildValidatorFactory();
    }

}
