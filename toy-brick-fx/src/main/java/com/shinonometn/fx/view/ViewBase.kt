package com.shinonometn.fx.view

import com.shinonometn.fx.app.ApplicationContext
import com.shinonometn.fx.dispatching.uiDispatch
import javafx.scene.Parent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * View [name] is optional.
 * */
abstract class ViewBase(name: String? = null) {

    /**
     * Name of this view controller.
     * It will be use as view name
     * */
    val name: String

    /**
     * View node of this controller
     */
    abstract val root: Parent

    init {
        this.name = name ?: ""
        uiDispatch {
            init()
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
    protected open suspend fun init() {
        // default do nothing
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> cssId(id: String): Lazy<T> = lazy {
        root.lookup("#$id") as T
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> cssId() = object : ReadOnlyProperty<ViewBase, T> {
        private var node: Any? = null
        override fun getValue(thisRef: ViewBase, property: KProperty<*>): T {
            if (node == null) node = thisRef.root.lookup("#${property.name}")
            return ((node ?: error("Node with cssId [${property.name}] not found!")) as T)
        }
    }
}