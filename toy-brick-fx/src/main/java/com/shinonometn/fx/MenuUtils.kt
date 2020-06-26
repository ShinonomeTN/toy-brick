package com.shinonometn.fx

import javafx.event.ActionEvent
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import java.util.*

typealias MenuCollection = MutableCollection<Menu>

fun createMenuCollection(block: MenuCollection.() -> Unit): LinkedList<Menu> {
    val menuCollection = LinkedList<Menu>()
    block.invoke(menuCollection)
    return menuCollection
}

fun MenuCollection.menu(name: String, block: Menu.() -> Unit): Menu {
    val menu = Menu(name)
    block.invoke(menu)
    add(menu)
    return menu
}

fun MenuCollection.addMenu(menu: Menu) {
    add(menu)
}

fun createMenu(name: String, block: Menu.() -> Unit) : Menu {
    val menu = Menu(name)
    block.invoke(menu)
    return menu
}

fun Menu.menuItem(name: String, onAction: (ActionEvent) -> Unit): MenuItem {
    val menuItem = MenuItem(name)
    menuItem.setOnAction { actionEvent -> onAction(actionEvent) }
    items.add(menuItem)
    return menuItem;
}

fun Menu.menuItem(name: String): MenuItem {
    val menuItem = MenuItem(name)
    items.add(menuItem)
    return menuItem;
}

fun Menu.menuSperator() {
    items.add(SeparatorMenuItem())
}
