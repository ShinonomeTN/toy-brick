package com.shinonometn.fx.demo

import com.shinonometn.fx.app.FxApp
import javafx.application.Application
import javafx.scene.layout.BorderPane

class DemoApp : FxApp(entryView = { BorderPane() })

fun main(args: Array<String>) {
    Application.launch(DemoApp::class.java, *args)
}