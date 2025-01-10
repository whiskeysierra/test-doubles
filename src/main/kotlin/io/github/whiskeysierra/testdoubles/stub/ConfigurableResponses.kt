package io.github.whiskeysierra.testdoubles.stub

/**
 * @see <a href="https://www.jamesshore.com/v2/projects/testing-without-mocks/testing-without-mocks#configurable-responses">Testing without Mocks: Configurable Responses</a>
 */
object ConfigurableResponses {
    fun <T> always(value: T) = generateSequence { value }.iterator()

    fun <T> once(vararg values: T) = values.iterator()
}
