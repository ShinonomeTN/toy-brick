package com.shinonometn.fx.controls

import javafx.scene.Node
import javafx.scene.layout.Pane

/*
*
* Parent/Children dsl
*
* */

infix fun Pane.beParentOf(child: Node) {
    children.add(child)
}

infix fun Pane.beParentOf(children: Collection<Node>) {
    this.children.addAll(children)
}

fun Pane.beParentOf(vararg children: Node) {
    this.children.addAll(children)
}