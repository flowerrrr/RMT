package de.flower.common.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testng.Assert
import org.testng.annotations.Test


@groovy.transform.TypeChecked
class ReflectionUtilTest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    def void testSuperClassProperties() {
        def o = new B()
        def s = ReflectionUtil.getProperty(o, "propB")
        log.info("propB: " + s)
        Assert.assertEquals(s, o.propB)
        s = ReflectionUtil.getField(o, "propA")
        log.info("propA: " + s)
        Assert.assertEquals(s, o.propA)
    }
}

class A {

    def propA = "bar"
}

class B extends A {

    def propB = "foo"
}
