package com.shinonometn.fx

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object JsonUtils {
    internal val objectMapper = ObjectMapper()

    fun toJsonTree(file: File): JsonNode {
        return objectMapper.readTree(file)
    }

    fun toJsonTree(inputStream: InputStream): JsonNode {
        return objectMapper.readTree(inputStream)
    }

    fun writeValue(outputStream: OutputStream, obj: Any) {
        objectMapper.writeValue(outputStream, obj)
    }
}

infix fun <T> JsonNode.asValue(type: Class<T>): T {
    return JsonUtils.objectMapper.treeAsTokens(this).readValueAs(type)
}

infix fun <T> JsonNode.asValue(type: TypeReference<T>): T {
    return JsonUtils.objectMapper.treeAsTokens(this).readValueAs(type)
}

/**
 * Read string as Json and convert to object as [clazz]
 *
 * */
infix fun <T> String.asJsonObjectFor(clazz: Class<T>): T? =
        JsonUtils.objectMapper.readerFor(clazz).readValue(this)

/**
 * Read stream as Json and convert to object as [clazz]
 *
 * */
infix fun <T> InputStream.readAsJsonObjectFor(clazz: Class<T>): T? =
        JsonUtils.objectMapper.readerFor(clazz).readValue(this)