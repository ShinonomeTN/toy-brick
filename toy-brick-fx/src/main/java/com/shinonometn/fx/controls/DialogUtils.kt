package com.shinonometn.fx.controls

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType

/*
* Dialogs
* */

typealias AlertType = Alert.AlertType

fun alert(
        alertType: Alert.AlertType = AlertType.INFORMATION,
        title: String = "",
        header: String = "",
        content: String = "",
        vararg buttons: ButtonType,
        configure: (Alert.() -> Unit)? = null
) = Alert(alertType).apply {
    this.title = title
    this.headerText = header
    this.contentText = content

    if (buttons.isNotEmpty()) with(buttonTypes) {
        clear()
        addAll(buttons)
    }

    configure?.invoke(this)
}