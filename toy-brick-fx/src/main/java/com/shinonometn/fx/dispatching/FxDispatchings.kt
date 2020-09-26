package com.shinonometn.fx.dispatching

import javafx.application.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun runInFx(task: () -> Unit) = Platform.runLater {
    task.invoke()
}

/**
 * Dispatch work to ui threads
**/
fun uiDispatch(block: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(Dispatchers.JavaFx).launch(block = block)

/**
 * Dispatch work to ui coroutine scope
 */
suspend fun <T> ui(block: suspend CoroutineScope.() -> T): T = withContext(Dispatchers.JavaFx) {
    block.invoke(this)
}

/**
 * Dispatch work to io threads
 **/
fun ioDispatch(block: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(Dispatchers.IO).launch(block = block)

/**
 * Dispatch work to io coroutine scope
 */
suspend fun <T> io(block: suspend CoroutineScope.() -> T): T = withContext(Dispatchers.IO) {
    block.invoke(this)
}

