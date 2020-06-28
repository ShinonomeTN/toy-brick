package com.shinonometn.fx.demo

import com.shinonometn.fx.view.ViewController
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField

class MainWindowController : ViewController("MainWindow") {
    @FXML lateinit var label: Label
    @FXML lateinit var textField: TextField
    @FXML lateinit var button: Button
    @FXML lateinit var exitButton: Button

    private var xOffset = 0.0
    private var yOffset = 0.0

    private var isMouseDragging = false

    override suspend fun afterInit() {
        label.textProperty().bind(textField.textProperty())

        view.apply {
            setOnMousePressed {
                xOffset = context.stage.x - it.screenX
                yOffset = context.stage.y - it.screenY
            }

            setOnMouseDragged {
                isMouseDragging = true

                if(!it.isPrimaryButtonDown) return@setOnMouseDragged

                context.stage.x = it.screenX + xOffset
                context.stage.y = it.screenY + yOffset

                it.consume()
            }

            setOnMouseReleased {
                isMouseDragging = false
            }
        }

        button.setOnAction {
            Alert(Alert.AlertType.INFORMATION).apply {
                headerText = "Hello"
                contentText = "You just inputted: ${textField.text}"
            }.showAndWait()
        }

        exitButton.setOnAction {
            context.app.onAppExitRequested()
        }
    }
}