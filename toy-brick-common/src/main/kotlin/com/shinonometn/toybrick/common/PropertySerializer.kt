package com.shinonometn.toybrick.common

interface PropertySerializer<T, V> {
    fun serialize(value : T) : V
    fun deserialize(raw : V) : T
}