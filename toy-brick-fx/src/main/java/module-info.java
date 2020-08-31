module toy.brick.fx {
    requires java.management;

    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires kotlinx.coroutines.javafx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.shinonometn.fx.app;
    exports com.shinonometn.fx.assets;
    exports com.shinonometn.fx.config;
    exports com.shinonometn.fx.controls;
    exports com.shinonometn.fx.dispatching;
    exports com.shinonometn.fx.view;

    exports com.shinonometn.fx;
}