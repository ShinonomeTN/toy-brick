package com.shinonometn.fx.controls

import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox

/*
* Container DSL
* */

fun vbox(spacing : Double = 0.0, configure : (VBox.() -> Unit)? = null) : VBox {
    val vbox = VBox(spacing)
    configure?.invoke(vbox)
    return vbox
}

fun stackPane(configure: (StackPane.() -> Unit)? = null): StackPane {
    val stackPane = StackPane()
    configure?.invoke(stackPane)
    return stackPane
}

fun borderPane(configure : (BorderPane.() -> Unit)? = null) : BorderPane {
    val borderPane = BorderPane()
    configure?.invoke(borderPane)
    return borderPane
}