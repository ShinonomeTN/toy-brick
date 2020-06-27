package com.shinonometn.fx.app

import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.Window

object ApplicationContext {
    /**
     * The app instance
     * */
    lateinit var app : FxApp
        internal set

    /**
     * Main window of this application
     * */
    lateinit var window : Window
        private set

    /**
     * Main scene of this application
     * */
    lateinit var scene : Scene
        private set

    /**
     * Main stage of this application
     * */
    lateinit var stage : Stage
        private set

    /**
     * Application Context initialization
     *
     * This function should be invoke only once and invoke by FxApp
     * */
    internal fun init(app : FxApp, primaryStage: Stage) {
        this.app = app
        this.window = primaryStage
        this.scene = primaryStage.scene
        this.stage = primaryStage
    }
}