package com.shinonometn.fx.app

import com.shinonometn.fx.dispatching.fxDispatch
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 *
 * The base of JavaFx App.
 *
 * Provided some default behavior of our app
 *
 * To initialize an app. EntryViewProvider([entryView]) and [init] actions is required.
 *
 * [entryView] provide an entry of the app (Main View)
 * [init] code block will be executed after FxApp initialized but before the Main Windows shown
 * */
abstract class FxApp(val entryView: () -> Parent, private val init: FxApp.(stage: Stage) -> Unit) : Application() {
    /**
     * The context of this app
     * */
    val context by lazy {
        ApplicationContext.context!!
    }

    /**
     * The root scene of this app
     * */
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

    /**
     * Apply css file on root view
     * */
    fun useCss(vararg name: String) {
        scene.stylesheets.addAll(listOf(*name))
    }

    /**
     * Add exiting works
     * */
    fun addExitAction(action: () -> Unit) {
        onExitActions.add(action)
    }

    /**
     * Remove exiting works
     * */
    fun removeExitAction(action: () -> Unit) {
        onExitActions.remove(action)
    }
}