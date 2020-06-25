package com.shinonometn.fx.app

import javafx.application.Application
import javafx.stage.Stage
import com.shinonometn.fx.*

abstract class FxApp(val onInit: FxApp.(stage: Stage) -> Unit) : Application() {
    override fun start(primaryStage: Stage) {
        ApplicationContext.context = FxAppContextImpl(primaryStage)

        onInit(primaryStage)

        fxDispatch {
            primaryStage.show()
        }
    }
}