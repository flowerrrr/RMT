package de.flower.test

import scala.collection.mutable.ListBuffer
import org.testng.Assert._
import org.testng.annotations.Test
import org.testng.annotations.Configuration
import org.scalatest.Assertions
import de.flower.rmt.model.MyTeamBE

class FooTest extends AbstractIntegrationTests with Assertions {

    var sb: StringBuilder = _
    var lb: ListBuffer[String] = _

    @Configuration(beforeTestMethod = true)
    def initialize() {
        sb = new StringBuilder("ScalaTest is ")
        lb = new ListBuffer[String]
    }

    @Test
    def testDao() {
        myTeamManager.save(new MyTeamBE())
        myTeamManager.loadAll()
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
