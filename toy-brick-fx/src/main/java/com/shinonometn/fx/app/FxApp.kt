package com.shinonometn.fx.app

import com.shinonometn.fx.JsonUtils
import com.shinonometn.fx.assets.resourceStream
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
 * To initialize an app. EntryViewProvider([entryView]) is required, and an optional [init] actions .
 *
 * [entryView] provide an entry of the app (Main View)
 * [init] code block will be executed after FxApp initialized but before the Main Windows shown
 * */
abstract class FxApp(val entryView: () -> Parent, private val init: (FxApp.(stage: Stage) -> Unit)? = null) : Application() {
    /**
     * The context of this app
     * */
    val context by lazy { ApplicationContext }

    /**
     * The root scene of this app
     * */
    val scene by lazy { ApplicationContext.scene }

    /**
     * The main window of this app
     * */
    val window by lazy { ApplicationContext.window }

    /**
     * The primary stage of this app
     * */
    val stage by lazy { ApplicationContext.stage }

    /**
     * Name of the application
     * */
    var name: String = "ToyBrickDemo"
        internal set

    /**
     * Should the application exit when main window close requested
     * */
    var exitOnClose = true

    private val onExitActions: MutableList<() -> Unit> = ArrayList()

    /**
     * Should the app show main window after initialized
     * */
    var showMainWindowAfterInit = true
        internal set

    /**
     * The start-up of ToyBrickFx framework
     * */
    override fun start(primaryStage: Stage) {
        ApplicationContext.init(this, primaryStage)

        /* Start config the app view */
        primaryStage.scene = Scene(entryView())

        defaultStageConfiguration()
        appJsonConfiguration()

        /* Invoke the user code block if provided */
        init?.let { it(primaryStage) }

        fxDispatch {
            if (showMainWindowAfterInit) primaryStage.show()
        }
    }

    /* Configuration helpers */

    /**
     * Basic configuration of the ToyBrickFx
     * */
    private fun defaultStageConfiguration() = with(stage) {
        /* The default window size */
        width = 640.0
        height = 480.0

        setOnCloseRequest {
            onAppExitRequested()
        }
    }

    /**
     * Configure the application with user's distribution settings
     * */
    private fun appJsonConfiguration() {
        /* If has custom setting for app, configure app */
        resourceStream("/app.json")?.let {
            fxAppConfigurationHandler(this, JsonUtils.toJsonTree(it)["application"])
        }
    }

    /**
     * Action to exit the application.
     * It will be invoke when the main window closing requested.
     *
     * Invoke directly will trigger the close phase
     * */
    fun onAppExitRequested() {
        onExitActions.forEach { it() }
        if (exitOnClose) Platform.exit()
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