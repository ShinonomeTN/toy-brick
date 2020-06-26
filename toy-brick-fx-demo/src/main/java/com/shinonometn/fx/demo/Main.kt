package com.shinonometn.fx.demo

import com.shinonometn.fx.app.FxApp
import com.shinonometn.fx.listenOnProperty
import com.shinonometn.fx.view.ViewController
import javafx.application.Application
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

private val textProperty = SimpleStringProperty("Test some text")

class DemoApp : FxApp(entryView = {

    object : ViewController(viewProvider = {
        BorderPane().apply {
            top = Label("Hello, JavaFx!").apply {
                font = Font.font(null, FontWeight.BOLD, null, 20.0)
                padding = Insets(3.0)
            }

            center = VBox().apply {
                children.addAll(
                        TextField().apply {
                            Bindings.bindBidirectional(textProperty(), textProperty)
                            maxWidth = Double.MAX_VALUE
                        },
                        Label().apply {
                            text = textProperty.get()
                            listenOnProperty(textProperty) { _,_, n ->
                                text = n
                            }
                            maxWidth = Double.MAX_VALUE
                        }
                )

                padding = Insets(3.0)
                spacing = 3.0
            }

            bottom = HBox().apply {
                children.add(Button("Click Me").apply {
                    setOnAction {
                        Alert(Alert.AlertType.INFORMATION).apply {
                            headerText = "Hello world!"
                            contentText = "You inputted: ${textProperty.get()}"
                        }.showAndWait()
                    }

                    maxWidth = Double.MAX_VALUE

                    HBox.setHgrow(this, Priority.ALWAYS)
                })

                padding = Insets(3.0)
                alignment = Pos.CENTER
            }
        }
    }){}.view as Parent

}, init = {
    context.windowTitle = "Hello toy-brick-fx-demo !"
})

fun main(args: Array<String>) {
    Application.launch(DemoApp::class.java, *args)
}