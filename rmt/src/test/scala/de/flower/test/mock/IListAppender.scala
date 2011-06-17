package de.flower.test.mock

import ch.qos.logback.classic.spi.ILoggingEvent

/**
 *
 * @author oblume
 */

trait IListAppender[T] {

    def getList: List[T]

    def reset()
}