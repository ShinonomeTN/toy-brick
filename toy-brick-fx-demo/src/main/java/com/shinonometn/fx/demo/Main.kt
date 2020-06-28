package com.shinonometn.fx.demo

import com.shinonometn.fx.app.FxApp
import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.paint.Color
import javafx.stage.StageStyle

class DemoApp : FxApp(entryView = { MainWindow.view as Parent }, init = {
    scene.fill = Color.TRANSPARENT
    stage.initStyle(StageStyle.TRANSPARENT)
})

fun main(args: Array<String>) {
    Application.launch(DemoApp::class.java, *args)
}