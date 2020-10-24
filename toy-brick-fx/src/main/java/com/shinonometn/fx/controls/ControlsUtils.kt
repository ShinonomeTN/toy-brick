package com.shinonometn.fx.controls

import javafx.scene.control.Label
import javafx.scene.control.ListView

/*
* Controls DSL
 */

fun label(text: String = "", configure: (Label.() -> Unit)? = null): Label {
    val label = Label(text)
    configure?.invoke(label)
    return label
}

fun <T> listView(configure: (ListView<T>.() -> Unit)? = null): ListView<T> {
    val listView = ListView<T>()
    configure?.invoke(listView)
    return listView
}