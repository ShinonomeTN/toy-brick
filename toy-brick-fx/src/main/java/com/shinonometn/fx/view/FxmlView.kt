package com.shinonometn.fx.view

import com.shinonometn.fx.assets.resource
import com.shinonometn.fx.dispatching.uiDispatch
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class FxmlView(
        val fxml: String
) : ViewBase(fxml.replace("/", ".").substring(0, fxml.lastIndexOf(".") - 1)) {
    /**
     * FXML loader
     * */
    internal val fxmlLoader: FXMLLoader

    final override val root: Parent

    /**
     * Create a ViewController with a FXML
     * [fxml] is the FXML file name, it's the path to FXML file.
     * @see com.shinonometn.fx.Fx
     * */
    init {
        fxmlLoader = FXMLLoader(resource("/$fxml"))
        root = fxmlLoader.load()
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> fxId(id: String): Lazy<T> = lazy {
        fxmlLoader.namespace[id] as T
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> fxId() = object : ReadOnlyProperty<FxmlView, T> {
        private var node: Any? = null
        override fun getValue(thisRef: FxmlView, property: KProperty<*>): T {
            if (node == null) node = thisRef.fxmlLoader.namespace[property.name]
            return ((node ?: error("Node with fxId [${property.name}] not found!")) as T)
        }
    }
}