package de.flower.test.mock

import ch.qos.logback.core.Appender

/**
 *
 * @author flowerrrr
 */

trait IListAppender[T] extends Appender[T] {

    def getList: List[T]

    def reset()
}