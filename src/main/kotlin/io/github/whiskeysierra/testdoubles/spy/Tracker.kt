package io.github.whiskeysierra.testdoubles.spy

import java.io.Closeable

interface Tracker<output> :
    List<output>,
    Closeable {
    fun clear()
}
