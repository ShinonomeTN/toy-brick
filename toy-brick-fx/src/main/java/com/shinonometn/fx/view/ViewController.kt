package com.shinonometn.fx.view

import javafx.beans.Observable
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.scene.Node
import javafx.scene.control.ButtonBase
import com.shinonometn.fx.app.ApplicationContext
import com.shinonometn.fx.dispatching.uiDispatch
import com.shinonometn.fx.fxLoadView

abstract class ViewController(val viewTemplateName: String) {
    /**
     * View node of this controller
     */
    val view: Node = fxLoadView(viewTemplateName)

    /**
     * Application Context
     * */
    val context = ApplicationContext.instance

    init {
        uiDispatch {
            afterInit()
        }
    }

    /**
     * Children can do something after initialized.
     * Those codes will be run in coroutine
     * */
    protected open suspend fun afterInit() {
        // default do nothing
    }

    /**
     * Some useful function for children
     * */

    protected fun <T> watchOn(
            vararg observableValue: ObservableValue<T>,
            action: (observable: ObservableValue<out T>, oldValue: T, newValue: T) -> Unit
    ) {
        observableValue.forEach {
            it.addListener { a, b, c -> action.invoke(a, b, c) }
        }
    }

    protected fun <T> watchOn(vararg observableValue: ObservableValue<T>, action: (newValue: T) -> Unit) {
        observableValue.forEach {
            it.addListener { _, _, c -> action.invoke(c) }
        }
    }

    protected infix fun <T> ObservableValue<T>.onChange(action: (newValue: T) -> Unit) {
        addListener { _, _, c -> action(c) }
    }

    protected infix fun Observable.onInvalidate(block: (Observable) -> Unit) {
        addListener { block(it) }
    }

    protected infix fun ButtonBase.onAction(block: (ActionEvent) -> Unit) {
        setOnAction { block(it) }
    }
}