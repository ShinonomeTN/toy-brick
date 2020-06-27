package com.shinonometn.fx.app

object ApplicationContext {
    internal lateinit var context: FxAppContextImpl

    lateinit var app : FxApp
        internal set

    val window by lazy {
        context.window
    }

    val instance: FxAppContextImpl by lazy {
        context
    }
}