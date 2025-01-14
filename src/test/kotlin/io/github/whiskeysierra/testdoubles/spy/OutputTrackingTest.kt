package io.github.whiskeysierra.testdoubles.spy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OutputTrackingTest {
    @Test
    fun `tracks output over time`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        assertThat(tracker).isEmpty()

        tracking.add("alice")
        assertThat(tracker).containsExactly("alice")

        tracking.add("bob")
        assertThat(tracker).containsExactly("alice", "bob")
    }

    @Test
    fun `tracks no past output`() {
        val tracking = OutputTracking<String>()

        tracking.add("alice")

        val tracker = tracking.track()
        assertThat(tracker).isEmpty()
    }

    @Test
    fun `clears output`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        tracking.add("alice")
        tracker.clear()

        assertThat(tracker).isEmpty()
    }

    @Test
    fun `tracks output after clear`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        tracking.add("alice")
        tracker.clear()
        tracking.add("bob")

        assertThat(tracker).containsExactly("bob")
    }

    @Test
    fun `stops tracking when closed`() {
        val tracking = OutputTracking<String>()
        val tracker = tracking.track()

        tracking.add("alice")
        tracker.close()
        tracking.add("bob")

        assertThat(tracker).containsOnly("alice")
        assertThat(tracker).doesNotContain("bob")
    }

    @Test
    fun `trackers are independent`() {
        val tracking = OutputTracking<String>()

        val tracker1 = tracking.track()
        tracking.add("alice")

        val tracker2 = tracking.track()
        tracking.add("bob")

        assertThat(tracker1).containsExactly("alice", "bob")
        assertThat(tracker2).containsExactly("bob")
    }
}
