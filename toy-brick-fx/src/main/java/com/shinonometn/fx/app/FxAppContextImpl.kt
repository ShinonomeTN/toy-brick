package com.shinonometn.fx.app

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.stage.Window
import java.io.File
import java.io.FileOutputStream

import com.shinonometn.fx.*
import com.shinonometn.fx.assets.resourceStream
import kotlin.properties.Delegates

class FxAppContextImpl(internal val rootStage: Stage, val app : FxApp) {

    val window: Window = rootStage

    val scene by lazy {
        ApplicationContext.instance.stage.scene
    }

    val stage = rootStage

    /**
     * Application configuration file path
     * */
    var configurationFile = File("./settings.json")
        private set

    /**
     * Set the configuration should persistent when app exit
     * */
    var persistentSettingsOnExit by Delegates.observable(true) { _, _ , new ->
        if(new) onEnableConfigPersistent() else onDisableConfigPersistent()
    }
    private fun onDisableConfigPersistent() {
        app.removeExitAction(saveConfigurationOnExitAction)
    }
    private fun onEnableConfigPersistent() {
        app.addExitAction(saveConfigurationOnExitAction)
    }
    private val saveConfigurationOnExitAction = { saveSettings() }

    /**
    * The current application configuration storage
    * */
    private val configurations = HashMap<String, AppSettingBean>()

    /**
     * Application configuration snapshot on start up
     * */
    private var appSettingTree: JsonNode? = null

    init {
        val settingFile = File("./settings.json")
        if (settingFile.exists()) {
            appSettingTree = JsonUtils.toJsonTree(settingFile)
        }

        app.addExitAction {
            saveSettings()
        }
    }

    /* Configure app with "app.json" */
    private fun handleAppInfo(tree: JsonNode) {
        val application = tree["application"] ?: return

        application["name"]?.let {
            stage.title = it.asText()
        }

        application["icon"]?.let {
            rootStage.icons.add(Image(resourceStream(it.asText())))
        }
    }

    /*
    *
    *
    * */

    fun getConfiguration(name: String): AppSettingBean? {
        return configurations[name]
    }

    fun saveSettings() {
        val destFile = "./settings.json"
        val map = configurations.entries.map { it.key to it.value.properties }.toMap()
        JsonUtils.objectMapper.writeValue(FileOutputStream(File(destFile)), map)
    }

    fun registerSetting(appSettingBean: AppSettingBean) {
        appSettingTree?.let {
            it[appSettingBean.name]
        }?.let {
            val type = object : TypeReference<Map<String, Any>>() {}
            appSettingBean.properties.putAll(it.asValue(type))
        }

        configurations[appSettingBean.name] = appSettingBean
    }
}