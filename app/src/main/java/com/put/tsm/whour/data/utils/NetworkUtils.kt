package com.put.tsm.whour.data.utils

import kotlinx.coroutines.delay

object NetworkUtils {
    suspend fun <T : Any> retry(
        times: Int = 3,
        initialDelay: Long = 1000L,
        maxDelay: Long = 1000L,
        factor: Double = 2.0,
        block: suspend () -> Result<T>
    ): Result<T> {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                when (val result = block()) {
                    is Result.Success -> return result
                    is Result.Error -> {
                        delay(currentDelay)
                        currentDelay = (currentDelay * factor).toLong().coerceAtLeast(maxDelay)
                    }
                }
            } catch (e: Exception) {

            }
        }
        return block()
    }
}