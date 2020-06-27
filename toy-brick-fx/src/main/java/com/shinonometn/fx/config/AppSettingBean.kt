package com.shinonometn.fx.config

import javafx.collections.FXCollections
import javafx.collections.ObservableMap

abstract class AppSettingBean(val name: String) {
    private val propertyMap: ObservableMap<String, Any> = FXCollections.observableHashMap()

    var properties: MutableMap<String, Any>
        get() = propertyMap
        set(value) = propertyMap.putAll(value)

    @Suppress("UNCHECKED_CAST")
    fun <T> get(name: String): T {
        return propertyMap[name] as T
    }

    fun set(name: String, value: Any) {
        propertyMap[name] = value
    }

    /*
    *
    * Helpers
    *
    * */
    protected fun <T> propertyWithDefault(value: T) = with(propertyMap) {
        withDefault {
            computeIfAbsent(it) { value }
        }
    }
}