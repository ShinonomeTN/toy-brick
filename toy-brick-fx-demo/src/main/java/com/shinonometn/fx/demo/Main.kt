package com.shinonometn.fx.demo

import com.shinonometn.fx.app.FxApp
import com.shinonometn.fx.launchFxApp
import javafx.scene.Parent
import javafx.scene.paint.Color
import javafx.stage.StageStyle

class DemoApp : FxApp(entryView = { MainWindow.view as Parent }, init = {
    scene.fill = Color.TRANSPARENT
    stage.initStyle(StageStyle.TRANSPARENT)
})

fun main(args: Array<String>) {
    launchFxApp<DemoApp>(args)
}