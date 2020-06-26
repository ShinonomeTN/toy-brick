package com.shinonometn.fx.app

import com.shinonometn.fx.dispatching.fxDispatch
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

abstract class FxApp(val entryView: () -> Parent, private val init: FxApp.(stage: Stage) -> Unit) : Application() {
    val context by lazy {
        ApplicationContext.context!!
    }

    val scene by lazy {
        ApplicationContext.instance.rootStage.scene
    }

    private val onExitActions: MutableList<() -> Unit> = ArrayList()

    override fun start(primaryStage: Stage) {
        ApplicationContext.context = FxAppContextImpl(primaryStage)

        /* Start config the app view */
        primaryStage.scene = Scene(entryView())
        defaultStageConfiguration(primaryStage)
        init(primaryStage)

        fxDispatch {
            primaryStage.show()
        }
    }

    private fun defaultStageConfiguration(stage: Stage) = with(stage) {
        /* The default window size */
        width = 640.0
        height = 480.0

        setOnCloseRequest {
            onAppExitRequested()
            Platform.exit()
        }
    }

    fun onAppExitRequested() {
        onExitActions.forEach { it() }
    }

    /* */

    fun useCss(vararg name: String) {
        scene.stylesheets.addAll(listOf(*name))
    }

    fun addExitAction(action: () -> Unit) {
        onExitActions.add(action)
    }

    fun removeExitAction(action: () -> Unit) {
        onExitActions.remove(action)
    }
}