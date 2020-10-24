package com.shinonometn.fx

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener

/*
*
*
*
* */

/**
 * Holding information about the listener registering
 */
class ListeningContext<T>(
        val listener: ChangeListener<T>,
        val observable: List<ObservableValue<T>>
) {
    fun unregister() = observable.forEach {
        it.removeListener(listener)
    }

    fun register() = observable.forEach {
        it.addListener(listener)
    }
}

/**
 * Add a listener on observable value
 * @return ListenerContext
 */
fun <T> listenOnProperty(
        observableValue: ObservableValue<T>,
        action: (observable: ObservableValue<out T>, oldValue: T, newValue: T) -> Unit
): ListeningContext<T> {
    val listener = ChangeListener<T> { a, b, c ->
        action.invoke(a, b, c)
    }
    observableValue.addListener(listener)
    return ListeningContext(listener, listOf(observableValue))
}

fun <T> listenOnProperty(
        vararg observableValue: ObservableValue<T>,
        action: (observable: ObservableValue<out T>, T, T) -> Unit
): ListeningContext<T> {
    val listener = ChangeListener<T> { a, b, c ->
        action.invoke(a, b, c)
    }
    observableValue.forEach {
        it.addListener(listener)
    }

    return ListeningContext(listener, observableValue.toList())
}

/**
 * Listen on an observable value, only take the new value
 */
fun <T> listenNewValue(
        observableValue: ObservableValue<T>,
        action: (newValue: T) -> Unit
): ListeningContext<T> {
    val listener = ChangeListener<T> { _, _, c ->
        action.invoke(c)
    }
    observableValue.addListener(listener)
    return ListeningContext(listener, listOf(observableValue))
}

fun <T> weakListenOnProperty(observableValue: ObservableValue<T>, action: (ObservableValue<out T>, T, T) -> Unit): WeakChangeListener<T> {
    val result = WeakChangeListener<T> { a, b, c -> action.invoke(a, b, c) }
    observableValue.addListener(result)
    return result
}

fun <T> weakListenOnProperty(vararg observableValue: ObservableValue<T>, action: (ObservableValue<out T>, T, T) -> Unit): WeakChangeListener<T> {
    val result = WeakChangeListener<T> { a, b, c -> action.invoke(a, b, c) }
    observableValue.forEach { it.addListener(result) }
    return result
}
