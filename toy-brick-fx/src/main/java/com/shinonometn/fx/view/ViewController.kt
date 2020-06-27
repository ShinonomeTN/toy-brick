package com.shinonometn.fx.view

import javafx.beans.Observable
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.scene.Node
import javafx.scene.control.ButtonBase
import com.shinonometn.fx.app.ApplicationContext
import com.shinonometn.fx.dispatching.uiDispatch
import com.shinonometn.fx.fxLoadView

abstract class ViewController {

    /**
     * Name of this view controller.
     * It will be use as view name
     * */
    private val name: String?

    /**
     * View node of this controller
     */
    val view: Node

    /**
     * Create a ViewController with a FXML
     * [fxmlViewName] is the FXML file name, it's the path to FXML file.
     * @see com.shinonometn.fx.Fx
     * */
    constructor(fxmlViewName: String) {
        this.name = fxmlViewName
        this.view = fxLoadView(fxmlViewName)
    }

    /**
     * Create a ViewController with [viewProvider]
     * View [name] is optional.
     * */
    constructor(viewProvider: () -> Node, name : String? = null) {
        this.view = viewProvider()
        this.name = name
    }

    /**
     * Application Context
     * */
    val context = ApplicationContext

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