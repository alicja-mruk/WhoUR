package com.put.tsm.whour.data.models

data class CategoryItem(
    val id: String,
    val categoryId: String,
    val description: String,
    val imageUrl: String,
    val type: String,
    val index: Long
)