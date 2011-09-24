package de.flower.common.util

import org.testng.annotations.Test
import org.slf4j.{LoggerFactory, Logger}
import reflect.BeanProperty

/**
 * 
 * @author flowerrrr
 */

class ReflectionUtilTest {

    var log: Logger = LoggerFactory.getLogger(this.getClass());

    @Test
    def testSuperClassProperties() {
        val o = new B()
        var s = ReflectionUtil.getProperty(o, "propB")
        log.info("propB: " + s)
        s = ReflectionUtil.getField(o, "propA")
        log.info("propA: " + s)

    }

}

class A {

    var propA: String = "bar"

}

class B(@BeanProperty var propB: String = "foo") extends A {

}
