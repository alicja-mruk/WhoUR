package com.put.tsm.whour.data.models

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val gender: Gender
)

enum class Gender(val value: String) {
    WOMAN("woman"),
    MAN("man")
}