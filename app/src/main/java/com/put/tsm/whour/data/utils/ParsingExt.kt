package com.put.tsm.whour.data.utils

import com.squareup.moshi.Moshi
import kotlin.reflect.KClass

fun <T : Any> mapToObject(map: MutableMap<String, Any>?, clazz: KClass<T>): T? {
    if (map == null) return null
    val constructor = clazz.constructors.first()
    val args = constructor
        .parameters.associateWith { map[it.name] }

    return constructor.callBy(args)
}

inline fun <reified T> Moshi.jsonToObjectOrNull(json: String?): T? =
    this.jsonToObjectOrNull(json, T::class.java)

fun <T> Moshi.jsonToObjectOrNull(json: String?, clazz: Class<T>): T? = try {
    adapter(clazz).fromJson(json)
} catch (ignored: Exception) {
    null
}