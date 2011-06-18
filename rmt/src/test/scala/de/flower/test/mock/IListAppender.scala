package de.flower.test.mock

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender

/**
 *
 * @author oblume
 */

trait IListAppender[T] extends Appender[T] {

    def getList: List[T]

    def reset()
}