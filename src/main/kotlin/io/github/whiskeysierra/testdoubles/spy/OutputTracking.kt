package io.github.whiskeysierra.testdoubles.spy

import java.util.concurrent.CopyOnWriteArrayList

/**
 * @see <a href="https://www.jamesshore.com/v2/projects/testing-without-mocks/testing-without-mocks#output-tracking">Testing without mocks: Output Tracking</a>
 */
class OutputTracking<Output> {
    private inner class Active(
        private val output: MutableList<Output> = ArrayList(),
    ) : Tracker<Output>,
        MutableList<Output> by output {
        override fun clear() = output.clear()

        override fun close() {
            trackers.remove(this)
        }
    }

    private val trackers = CopyOnWriteArrayList<Active>()

    fun emit(data: Output) = trackers.forEach { it.add(data) }

    fun track(): Tracker<Output> = Active().also(trackers::add)
}
