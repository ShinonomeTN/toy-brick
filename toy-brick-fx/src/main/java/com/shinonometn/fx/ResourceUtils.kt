package com.shinonometn.fx

import java.io.InputStream
import java.net.URL

fun <T : Any> T.resource(name : String) : URL = javaClass.getResource(name)

fun <T : Any> T.resourceAsStream(name : String) : InputStream? = javaClass.getResourceAsStream(name)