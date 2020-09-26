package com.shinonometn.fx

import com.shinonometn.fx.app.FxApp
import com.shinonometn.fx.assets.resource
import javafx.application.Application
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import kotlin.reflect.KClass

object Fx {
    private var fxmlClassPaths = "views"

    val FXML_CLASS_PATH: String
        get() = fxmlClassPaths
}

/**
 * Launch the FxApp
 * */
fun <A : FxApp> launchFxApp(appClazz: KClass<A>, args: Array<String>) {
    Application.launch(appClazz.java, *args)
}

/*
*
* Basic functions
*
* */

/**
 *
 * Load FXML file for view from resources package (default is 'views.default')
 * Pass view name as [viewDomainName] (file name without '.fxml')
 *
 * */
fun <T : Node> T.fxLoadView(viewDomainName: String): T = FXMLLoader(resource(getViewPath(viewDomainName))).apply {
    setRoot(this@fxLoadView)
    setController(this@fxLoadView)
}.load()

fun <T : Any, N : Node> T.fxLoadView(viewDomainName: String): N = FXMLLoader(resource(getViewPath(viewDomainName))).also {
    it.setController(this)
}.load()

/**
 *
 * Get stylesheet resource path
 * Pass file name without '.css' to [name]
 *
 * */
fun asStylesheetName(name: String): String {
    return Fx::class.java.getResource(resourcePathFromDomainName("$name.css")).toExternalForm()
}

/*
*
* Private procedure
*
* */

private fun resourcePathFromDomainName(path: String): String {
    return "/${Fx.FXML_CLASS_PATH.replace(".", "/")}/" + path
}

private fun getViewPath(viewDomainName: String): String {
    if (viewDomainName.contains("/")) throw IllegalArgumentException("View Domain name should not contains '/'")
    return ".${Fx.FXML_CLASS_PATH}.$viewDomainName".replace(".", "/") + ".fxml"
}