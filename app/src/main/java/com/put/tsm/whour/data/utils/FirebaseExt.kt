package com.put.tsm.whour.data.utils

import kotlin.reflect.KClass

fun <T : Any> mapToObject(map: MutableMap<String, Any>?, clazz: KClass<T>): T? {
    if (map == null) return null
    val constructor = clazz.constructors.first()

    val args = constructor
        .parameters.associateWith { map.get(it.name) }

    return constructor.callBy(args)
}