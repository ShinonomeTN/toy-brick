package com.shinonometn.fx

import javafx.application.Platform
import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import javafx.beans.value.WritableValue
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KProperty

object Fx {
    private var fxmlClassPaths = "views"

    val FXML_CLASS_PATH: String
        get() = fxmlClassPaths
}

/*
*
* Basic functions
*
* */

/**
 *
 * Load FXML file for view from resources package (default is 'views.default')
 * Pass view name as [viewDomainName] (file name without '.fxml')
 *
 * */
fun <T : Node> T.fxLoadView(viewDomainName: String): T = FXMLLoader(javaClass.getResource(getViewPath(viewDomainName))).apply {
    setRoot(this@fxLoadView)
    setController(this@fxLoadView)
}.load()

fun <T : Any, N : Node> T.fxLoadView(viewDomainName: String): N =
        FXMLLoader(resource(getViewPath(viewDomainName))).also {
            it.setController(this)
        }.load()

fun fxDispatch(task: () -> Unit) {
    Platform.runLater { task.invoke() }
}

fun uiDispatch(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(Dispatchers.JavaFx).apply {
    launch(context = this.coroutineContext, block = block)
}

suspend fun <R> uiWork(block: suspend CoroutineScope.() -> R) = withContext(Dispatchers.JavaFx, block)

fun ioDispatch(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(Dispatchers.IO).apply {
    launch(context = this.coroutineContext, block = block)
}

suspend fun <R> ioWork(block: suspend CoroutineScope.() -> R) = withContext(Dispatchers.IO, block)

/**
 *
 * Get stylesheet resource path
 * Pass file name without '.css' to [name]
 *
 * */
fun asStylesheetName(name: String): String {
    return Fx::class.java.getResource(resourcePathFromDomainName("$name.css")).toExternalForm()
}

/*
*
*
*
* */

fun <T> listenOnProperty(observableValue: ObservableValue<T>, action: (ObservableValue<out T>, T, T) -> Unit) {
    observableValue.addListener { a, b, c -> action.invoke(a, b, c) }
}

fun <T> listenOnProperty(vararg observableValue: ObservableValue<T>, action: (ObservableValue<out T>, T, T) -> Unit) {
    observableValue.forEach {
        it.addListener { a, b, c -> action.invoke(a, b, c) }
    }
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

fun resourceUrl(path: String): String {
    return Fx.javaClass.getResource(path).toExternalForm()
}

/*
*
* Private procedure
*
* */

private fun resourcePathFromDomainName(path: String): String {
    return "/${Fx.FXML_CLASS_PATH.replace(".", "/")}/" + path
}

private fun getViewPath(viewDomainName: String): String {
    if (viewDomainName.contains("/")) throw IllegalArgumentException("View Domain name should not contains '/'")
    return ".${Fx.FXML_CLASS_PATH}.$viewDomainName".replace(".", "/") + ".fxml"
}

/*
*
* Operators to convert FX properties
*
* */
operator fun <T> ObservableValue<T>.getValue(thisRef: Any, property: KProperty<*>): T = value

operator fun <T> Property<T>.getValue(thisRef: Any, property: KProperty<*>): T = value
operator fun ReadOnlyDoubleProperty.getValue(thisRef: Any, property: KProperty<*>): Double = value

operator fun <T> WritableValue<T>.setValue(thisRef: Any, property: KProperty<*>, value: T) = setValue(value)