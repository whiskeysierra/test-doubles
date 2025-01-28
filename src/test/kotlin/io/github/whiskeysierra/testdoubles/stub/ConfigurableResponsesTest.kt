package io.github.whiskeysierra.testdoubles.stub

import io.github.whiskeysierra.testdoubles.stub.ConfigurableResponses.always
import io.github.whiskeysierra.testdoubles.stub.ConfigurableResponses.once
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ConfigurableResponsesTest {

    @DisplayName("always")
    @Nested
    inner class Always {

        @Test
        fun `throws if no response was configured`() {
            val values = always("value")

            assertThat(values.next()).isEqualTo("value")
            assertThat(values.next()).isEqualTo("value")
            assertThat(values.next()).isEqualTo("value")
        }

        @Test
        fun `returns configured response forever`() {
            val values = always("value")

            assertThat(values.next()).isEqualTo("value")
            assertThat(values.next()).isEqualTo("value")
            assertThat(values.next()).isEqualTo("value")
        }

        @Test
        fun `returns configured responses forever`() {
            val values = always("1st", "2nd", "3rd")

            repeat(3) { values.next() }

            assertThat(values.next()).isEqualTo("1st")
            assertThat(values.next()).isEqualTo("2nd")
            assertThat(values.next()).isEqualTo("3rd")
        }

        @Test
        fun `supports responses of different types`() {
            val values = always(1, 2.0, "three")

            assertThat(values.next()).isEqualTo(1)
            assertThat(values.next()).isEqualTo(2.0)
            assertThat(values.next()).isEqualTo("three")
        }
    }

    @DisplayName("once")
    @Nested
    inner class Once {

        @Test
        fun `throws if no response was configured`() {
            val values = once<String>()

            assertThatThrownBy { values.next() }
                .isInstanceOf(NoSuchElementException::class.java)
        }

        @Test
        fun `returns configured response`() {
            val values = once("1st", "2nd", "3rd")

            assertThat(values.next()).isEqualTo("1st")
            assertThat(values.next()).isEqualTo("2nd")
            assertThat(values.next()).isEqualTo("3rd")
        }

        @Test
        fun `throws if no more configured responses`() {
            val values = once("1st", "2nd", "3rd")

            repeat(3) { values.next() }

            assertThat(values).isExhausted
            assertThatThrownBy { values.next() }
                .isInstanceOf(NoSuchElementException::class.java)
        }

        @Test
        fun `supports responses of different types`() {
            val values = once(1, 2.0, "three")

            assertThat(values.next()).isEqualTo(1)
            assertThat(values.next()).isEqualTo(2.0)
            assertThat(values.next()).isEqualTo("three")
        }
    }
}
