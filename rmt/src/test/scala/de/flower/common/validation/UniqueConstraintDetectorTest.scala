package de.flower.common.validation

import org.testng.annotations.Test
import org.testng.Assert._
import javax.persistence.{UniqueConstraint, Table, Column}
import unique.UniqueConstraintDetector

/**
 * 
 * @author oblume
 */

class UniqueConstraintDetectorTest {

    /**
     * Tests that validator analyzes and validates @Column(unique = true) constraints.
     */
    @Test
    def testConstraintDetector() {
        val constraints = UniqueConstraintDetector.detectConstraints(classOf[TestEntity])
        assertEquals(constraints.size(), 2)
    }
}

@Table(uniqueConstraints = Array(new UniqueConstraint(columnNames = Array("col1", "col2"))))
class TestEntity {

    @Column(unique = true)
    var uniqueColumn: String = _

    @Column(unique = false)
    var nonUniqueColumn: String = _

    @Column
    var nonUniqueColumn2: String = _
}
