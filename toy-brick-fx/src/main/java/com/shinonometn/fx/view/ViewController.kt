package com.shinonometn.fx.view

import javafx.beans.Observable
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.scene.Node
import javafx.scene.control.ButtonBase
import com.shinonometn.fx.app.ApplicationContext
import com.shinonometn.fx.assets.resource
import com.shinonometn.fx.dispatching.uiDispatch
import com.shinonometn.fx.fxLoadView
import javafx.fxml.FXMLLoader

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
     * FXML loader
     * */
    lateinit var fxmlLoader : FXMLLoader

    /**
     * Create a ViewController with a FXML
     * [fxmlViewName] is the FXML file name, it's the path to FXML file.
     * @see com.shinonometn.fx.Fx
     * */
    constructor(fxml: String) {
        this.name = fxml.replace("/",".").substring(0, fxml.lastIndexOf(".") - 1)
        fxmlLoader = FXMLLoader(resource("/$fxml"))
        this.view = fxmlLoader.load()

        uiDispatch {
            afterInit()
        }
    }

    /**
     * Create a ViewController with [viewProvider]
     * View [name] is optional.
     * */
    constructor(viewProvider: () -> Node, name : String? = null) {
        this.view = viewProvider()
        this.name = name

        uiDispatch {
            afterInit()
        }
    }

    /**
     * Application Context
     * */
    val context = ApplicationContext

    /**
     * Children can do something after initialized.
     * Those codes will be run in coroutine
     * */
    protected open suspend fun afterInit() {
        // default do nothing
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> fxId(id : String) : Lazy<T> = lazy {
        fxmlLoader.namespace[id] as T
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> cssId(id : String) : Lazy<T> = lazy {
        view.lookup("#${id}") as T
    }
}