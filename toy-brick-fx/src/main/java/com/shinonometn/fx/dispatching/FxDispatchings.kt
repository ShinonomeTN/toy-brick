package com.shinonometn.fx.dispatching

import javafx.application.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun fxDispatch(task: () -> Unit) {
    Platform.runLater { task.invoke() }
}

fun uiDispatch(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(Dispatchers.JavaFx).apply {
    launch(context = this.coroutineContext, block = block)
}
suspend fun <R> uiDispatch(block: suspend CoroutineScope.() -> R) = withContext(Dispatchers.JavaFx, block)

fun ioDispatch(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(Dispatchers.IO).apply {
    launch(context = this.coroutineContext, block = block)
}
suspend fun <R> ioDispatch(block: suspend CoroutineScope.() -> R) = withContext(Dispatchers.IO, block)