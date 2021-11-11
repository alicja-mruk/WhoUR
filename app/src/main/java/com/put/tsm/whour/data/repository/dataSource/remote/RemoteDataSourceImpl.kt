package com.put.tsm.whour.data.repository.dataSource.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.utils.Result
import com.put.tsm.whour.data.utils.mapToObject
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {
    private val db = Firebase.firestore

    override suspend fun getAllCategories(forceUpdate: Boolean): Result<List<Category>> =
        suspendCoroutine { continuation ->
            db.collection(CATEGORIES)
                .get()
                .addOnSuccessListener { snapshot ->
                    val documents = snapshot.documents.map { it.data }
                    val categories = documents.map { mapToObject(it, Category::class) }
                    continuation.resume(Result.Success(categories.filterNotNull()))
                }.addOnFailureListener {
                    continuation.resume(Result.Error(it))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.Error(CancellationException()))
                }
        }

    override suspend fun getAllItems(forceUpdate: Boolean): Result<List<CategoryItem>> =
        suspendCoroutine { continuation ->
            db.collection(ITEMS).get()
                .addOnSuccessListener { snapshot ->
                    val documents = snapshot.documents.map { it.data }
                    val items = documents.map { mapToObject(it, CategoryItem::class) }
                    continuation.resume(Result.Success(items.filterNotNull()))
                }.addOnFailureListener {
                    continuation.resume(Result.Error(it))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.Error(CancellationException()))
                }
        }

    override suspend fun getItemsFromCategory(
        categoryId: String,
        forceUpdate: Boolean
    ): Result<List<CategoryItem>> = suspendCoroutine { continuation ->
        db.collection(ITEMS).whereEqualTo(CATEGORY_ID, categoryId).get()
            .addOnSuccessListener { snapshot ->
                val documents = snapshot.documents.map { it.data }
                val items = documents.map { mapToObject(it, CategoryItem::class) }
                continuation.resume(Result.Success(items.filterNotNull()))
            }.addOnFailureListener {
                continuation.resume(Result.Error(it))
            }
            .addOnCanceledListener {
                continuation.resume(Result.Error(CancellationException()))
            }
    }

    companion object {
        const val CATEGORIES: String = "categories"
        const val CATEGORY_ID: String = "categoryId"
        const val ITEMS: String = "items"
    }
}