package de.flower.rmt.ui.app

import org.apache.wicket.spring.injection.annot.SpringComponentInjector
import org.springframework.context.ApplicationContext

/**
 * @author flowerrrr
 */
class TestApplication(val ctx: ApplicationContext) extends RMTApplication {

    protected override def getSpringComponentInjector: SpringComponentInjector = {
        return new SpringComponentInjector(this, ctx)
    }

}