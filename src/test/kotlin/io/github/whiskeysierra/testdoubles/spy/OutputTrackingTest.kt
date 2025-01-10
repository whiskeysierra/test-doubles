package io.github.whiskeysierra.testdoubles.spy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OutputTrackingTest {
    @Test
    fun `tracks output over time`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        assertThat(tracker).isEmpty()

        tracking.emit("alice")
        assertThat(tracker).containsExactly("alice")

        tracking.emit("bob")
        assertThat(tracker).containsExactly("alice", "bob")
    }

    @Test
    fun `tracks no past output`() {
        val tracking = OutputTracking<String>()

        tracking.emit("alice")

        val tracker = tracking.track()
        assertThat(tracker).isEmpty()
    }

    @Test
    fun `clears output`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        tracking.emit("alice")
        tracker.clear()

        assertThat(tracker).isEmpty()
    }

    @Test
    fun `tracks output after clear`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        tracking.emit("alice")
        tracker.clear()
        tracking.emit("bob")

        assertThat(tracker).containsExactly("bob")
    }

    @Test
    fun `stops tracking when closed`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        tracking.emit("alice")
        tracker.close()
        tracking.emit("bob")

        assertThat(tracker).containsOnly("alice")
        assertThat(tracker).doesNotContain("bob")
    }

    @Test
    fun `trackers are independent`() {
        val tracking = OutputTracking<String>()

        val tracker1 = tracking.track()
        tracking.emit("alice")

        val tracker2 = tracking.track()
        tracking.emit("bob")

        assertThat(tracker1).containsExactly("alice", "bob")
        assertThat(tracker2).containsExactly("bob")
    }
}
