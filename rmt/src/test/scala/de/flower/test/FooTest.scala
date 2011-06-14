package de.flower.test

import scala.collection.mutable.ListBuffer
import org.testng.Assert._
import org.scalatest.Assertions
import de.flower.rmt.model.Team
import org.testng.annotations.{BeforeMethod, Test, Configuration}
import reflect.Method

class FooTest extends  Assertions {

    var sb: StringBuilder = _
    var lb: ListBuffer[String] = _

    @Configuration(beforeTestMethod = true)
    def initialize() {
        sb = new StringBuilder("ScalaTest is ")
        lb = new ListBuffer[String]
    }

   @Test def verifyEasy() {
        // Uses TestNG-style assertions
        sb.append("easy!")
        assertEquals("ScalaTest is easy!", sb.toString)
        assertTrue(lb.isEmpty)
        lb += "sweet"
        try {
            "verbose".charAt(-1)
            fail()
        }
        catch {
            case e: StringIndexOutOfBoundsException => // Expected
        }
    }

    @Test def verifyFun() {
        // Uses ScalaTest assertions
        sb.append("fun!")
        assert(sb.toString === "ScalaTest is fun!")
        assert(lb.isEmpty)
        lb += "sweeter"
        intercept[StringIndexOutOfBoundsException] {
            "concise".charAt(-1)
        }
    }
}