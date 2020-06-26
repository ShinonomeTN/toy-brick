package com.shinonometn.fx.assets

import com.shinonometn.fx.Fx
import java.io.InputStream
import java.net.URL

fun <T : Any> T.resource(name: String): URL = javaClass.getResource(name)

fun <T : Any> T.resourceStream(name: String): InputStream? = javaClass.getResourceAsStream(name)

fun <T : Any> T.resourceUrl(path: String): String = Fx.javaClass.getResource(path).toExternalForm()