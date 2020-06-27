package com.shinonometn.fx.assets

import com.shinonometn.fx.Fx
import java.io.InputStream
import java.net.URL

fun <T : Any> T.resource(name: String): URL = javaClass.getResource(name)
fun resource(name: String): URL = Fx.javaClass.getResource(name)

fun <T : Any> T.resourceStream(name: String): InputStream? = javaClass.getResourceAsStream(name)
fun resourceStream(name: String): InputStream? = Fx.javaClass.getResourceAsStream(name)

fun <T : Any> T.resourceUrl(path: String): String = javaClass.getResource(path).toExternalForm()
fun resourceUrl(path: String): String = Fx.javaClass.getResource(path).toExternalForm()
