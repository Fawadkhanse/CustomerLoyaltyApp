package org.example.project.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject


// Configure JSON instance
val jsonInstance = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
}

// Basic JSON extensions
inline fun <reified T> String.toPojo(): T {
    return jsonInstance.decodeFromString<T>(this)
}

inline fun <reified T> T.toJson(): String {
    return jsonInstance.encodeToString(this)
}
inline fun <reified T> String.toPojoOrNull(): T? =
    try {
        jsonInstance.decodeFromString<T>(this)
    } catch (e: Exception) {
        null
    }


fun String.toJsonObject(): JsonObject {
    return Json.parseToJsonElement(this).jsonObject
}

// ArrayList extension functions
inline fun <reified T> String.toArrayList(): ArrayList<T> {
    val list = jsonInstance.decodeFromString<List<T>>(this)
    return ArrayList(list)
}

inline fun <reified T> ArrayList<T>.toJson(): String {
    return jsonInstance.encodeToString(this)
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

fun <T> ArrayList<T>.addIfNotExists(item: T): Boolean {
    return if (!contains(item)) {
        add(item)
        true
    } else {
        false
    }
}

fun <T> ArrayList<T>.removeIfExists(item: T): Boolean {
    return remove(item)
}

fun <T> ArrayList<T>.addAllIfNotExists(items: Collection<T>): Boolean {
    val newItems = items.filter { !contains(it) }
    return if (newItems.isNotEmpty()) {
        addAll(newItems)
        true
    } else {
        false
    }
}

// Safe operations
fun <T> ArrayList<T>.safeGet(index: Int): T? {
    return if (index in 0 until size) get(index) else null
}

fun <T> ArrayList<T>.safeRemoveAt(index: Int): T? {
    return if (index in 0 until size) removeAt(index) else null
}