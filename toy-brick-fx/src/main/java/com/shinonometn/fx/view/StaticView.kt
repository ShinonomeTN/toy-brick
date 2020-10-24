package com.shinonometn.fx.view

import javafx.scene.Node

open class StaticView<T : ViewBase>(viewInitializer: () -> T) {
    val instance: T by lazy(viewInitializer)
    val view: Node = instance.root
    val context by lazy { instance.context }
}