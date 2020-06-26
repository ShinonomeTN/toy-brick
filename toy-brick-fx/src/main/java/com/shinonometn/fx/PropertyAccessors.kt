package com.shinonometn.fx

import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.value.ObservableValue
import javafx.beans.value.WritableValue
import kotlin.reflect.KProperty

/*
*
* Operators to convert FX properties
*
* */
operator fun <T> ObservableValue<T>.getValue(thisRef: Any, property: KProperty<*>): T = value

operator fun <T> Property<T>.getValue(thisRef: Any, property: KProperty<*>): T = value
operator fun ReadOnlyDoubleProperty.getValue(thisRef: Any, property: KProperty<*>): Double = value

operator fun <T> WritableValue<T>.setValue(thisRef: Any, property: KProperty<*>, value: T) = setValue(value)