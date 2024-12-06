package pt.isec.amov.tp201101617120180146432019139650.data.repos

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import pt.isec.amov.tp201101617120180146432019139650.data.models.Category


class CategoryRepository {

    private val db = Firebase.firestore

    fun saveCategory(category: Category, onResult: (Boolean, Throwable?) -> Unit) {
        val c = db.collection(Category.COLLECTION_NAME).document(category.id)
        db.runTransaction { transaction ->
            val doc = transaction.get(c)
            if(doc.exists()){
                //if exists, updates document
                transaction.update(c, Category.TITLE, category.title)
                transaction.update(c, Category.DESCRIPTION, category.description)
                transaction.update(c, Category.IMAGE, category.image)
                null
            } else {
                //if not, creates one
                c.set(hashMapOf(
                    Category.ID to category.id,
                    Category.TITLE to category.title,
                    Category.DESCRIPTION to category.description,
                    Category.IMAGE to category.image
                ))
            }
        }.addOnCompleteListener { result ->
            onResult(result.isSuccessful, result.exception)
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return try {
            db.collection(Category.COLLECTION_NAME)
                .get()
                .await()
                .documents
                .mapNotNull {
                    it.toCategory()
                }
        } catch (e: Exception) {
            // Trate exceções ou retorne uma lista vazia em caso de erro
            emptyList()
        }
    }


}

fun DocumentSnapshot.toCategory() : Category {
    val id = this.getString(Category.ID) ?: ""
    val title = this.getString(Category.TITLE) ?: ""
    val description = this.getString(Category.DESCRIPTION)
    val image = this.getString(Category.IMAGE)
    return Category(
        id = id, title = title, description = description, image = image
    )
}