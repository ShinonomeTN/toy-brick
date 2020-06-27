package com.shinonometn.fx.app

import java.io.File

interface ConfigurationStorageStrategy {
    val name : String
    fun fileProvider() : File
}