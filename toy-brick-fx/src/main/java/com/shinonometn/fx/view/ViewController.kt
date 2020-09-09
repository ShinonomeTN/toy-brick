package com.shinonometn.fx.view

import com.shinonometn.fx.app.ApplicationContext
import com.shinonometn.fx.assets.resource
import com.shinonometn.fx.dispatching.uiDispatch
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

typealias ViewProvider = () -> Parent

abstract class ViewController {

    /**
     * Name of this view controller.
     * It will be use as view name
     * */
    private val name: String?

    /**
     * View node of this controller
     */
    val view: Parent

    /**
     * FXML loader
     * */
    lateinit var fxmlLoader: FXMLLoader

    /**
     * Create a ViewController with a FXML
     * [fxmlViewName] is the FXML file name, it's the path to FXML file.
     * @see com.shinonometn.fx.Fx
     * */
    constructor(fxml: String) {
        this.name = fxml.replace("/", ".").substring(0, fxml.lastIndexOf(".") - 1)
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
    constructor(viewProvider: ViewProvider, name: String? = null) {
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
    protected fun <T> fxId(id: String): Lazy<T> = lazy {
        fxmlLoader.namespace[id] as T
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> fxId() = object : ReadOnlyProperty<ViewController, T> {
        private var node : Any? = null
        override fun getValue(thisRef: ViewController, property: KProperty<*>): T {
            if(node == null) node = thisRef.fxmlLoader.namespace[property.name]
            return ((node ?: error("Node with id [${property.name}] not found!")) as T)
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> cssId(id: String): Lazy<T> = lazy {
        view.lookup("#${id}") as T
    }
}