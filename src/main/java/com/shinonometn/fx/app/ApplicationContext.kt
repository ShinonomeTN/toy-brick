package com.shinonometn.fx.app

object ApplicationContext {
    internal var context: FxAppContextImpl? = null

    val instance: FxAppContextImpl by lazy {
        context!!
    }
}