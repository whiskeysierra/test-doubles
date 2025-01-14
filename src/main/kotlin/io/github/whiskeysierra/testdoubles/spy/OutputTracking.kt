package io.github.whiskeysierra.testdoubles.spy

import java.util.concurrent.CopyOnWriteArrayList

/**
 * @see <a href="https://www.jamesshore.com/v2/projects/testing-without-mocks/testing-without-mocks#output-tracking">Testing without mocks: Output Tracking</a>
 */
class OutputTracking<Output> {
    private class Subscription<Output>(
        private val tracking: OutputTracking<Output>,
        private val outputs: MutableList<Output> = ArrayList(),
    ) : Tracker<Output>,
        List<Output> by outputs {
        fun add(output: Output) {
            outputs.add(output)
        }

        override fun clear() = outputs.clear()

        override fun close() {
            tracking.remove(this)
        }
    }

    private val subscriptions = CopyOnWriteArrayList<Subscription<Output>>()

    fun add(output: Output): Unit = subscriptions.forEach { it.add(output) }

    fun track(): Tracker<Output> = Subscription(this).also(subscriptions::add)

    internal fun remove(subscription: Tracker<Output>) = subscriptions.remove(subscription)
}
