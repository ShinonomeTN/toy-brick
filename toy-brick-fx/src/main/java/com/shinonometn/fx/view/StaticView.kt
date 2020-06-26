package com.shinonometn.fx.view

import javafx.scene.Node

open class StaticView<T : ViewController>(viewInitializer: () -> T) {
    val instance: T by lazy(viewInitializer)
    val view: Node = instance.view
    val context by lazy { instance.context }
}