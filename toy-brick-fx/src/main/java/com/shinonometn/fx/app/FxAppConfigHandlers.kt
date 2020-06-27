package com.shinonometn.fx.app

import com.fasterxml.jackson.databind.JsonNode
import com.shinonometn.fx.assets.resourceStream
import javafx.scene.image.Image

private typealias ConfigHandler = (FxApp, JsonNode) -> Unit

private val handlerMap = mapOf<String, ConfigHandler>(
        "name" to { app, node -> app.name = node.asText() },
        "windowTitle" to { app, node -> app.stage.title = node.asText() },
        "icon" to { app, node -> app.stage.icons.add(Image(resourceStream(node.asText()))) },
        "exitOnClose" to { app, node -> app.exitOnClose = node.asBoolean() },
        "showMainWindowAfterInit" to { app, node -> app.showMainWindowAfterInit = node.asBoolean() }
)

fun fxAppConfigurationHandler(app : FxApp, json: JsonNode) {
    for (key in handlerMap.keys) {
        json[key]?.let {
            (handlerMap[key] ?: error("Unexpected no config handler for $key"))(app, it)
        }
    }
}