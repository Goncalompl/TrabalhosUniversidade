package pt.isec.amov.tp201101617120180146432019139650.data.repos

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import pt.isec.amov.tp201101617120180146432019139650.data.models.PointOfInterest

class PointOfInterestRepository {
    private val db = Firebase.firestore

    fun savePointOfInterest(pointOfInterest: PointOfInterest, onResult: (Boolean, Throwable?) -> Unit) {
        val c = db.collection(PointOfInterest.COLLECTION_NAME).document(pointOfInterest.id)
        db.runTransaction { transaction ->
            val doc = transaction.get(c)
            if(doc.exists()){
                //if exists, updates document
                transaction.update(c, PointOfInterest.TITLE, pointOfInterest.title)
                transaction.update(c, PointOfInterest.DESCRIPTION, pointOfInterest.description)
                transaction.update(c, PointOfInterest.LATITUDE, pointOfInterest.latitude)
                transaction.update(c, PointOfInterest.LONGITUDE, pointOfInterest.longitude)
                transaction.update(c, PointOfInterest.CATEGORY_ID, pointOfInterest.categoryId)
                transaction.update(c, PointOfInterest.CATEGORY_TITLE, pointOfInterest.categoryTitle)
                transaction.update(c, PointOfInterest.LOCATION_ID, pointOfInterest.locationId)
                transaction.update(c, PointOfInterest.LOCATION_TITLE, pointOfInterest.locationTitle)
                //transaction.update(c, PointOfInterest.IMAGE, pointOfInterest.image)

                //transaction.update(c, PointOfInterest.STATE, pointOfInterest.state)
                null
            } else {
                //if not, creates one
                c.set(hashMapOf(
                    PointOfInterest.ID to pointOfInterest.id,
                    PointOfInterest.TITLE to pointOfInterest.title,
                    PointOfInterest.DESCRIPTION to pointOfInterest.description,
                    PointOfInterest.LATITUDE to pointOfInterest.latitude,
                    PointOfInterest.LONGITUDE to pointOfInterest.longitude,
                    PointOfInterest.CATEGORY_ID to pointOfInterest.categoryId,
                    PointOfInterest.CATEGORY_TITLE to pointOfInterest.categoryTitle,
                    PointOfInterest.LOCATION_ID to pointOfInterest.locationId,
                    PointOfInterest.LOCATION_TITLE to pointOfInterest.locationTitle,
                    //PointOfInterest.IMAGE to pointOfInterest.image,
                    //PointOfInterest.STATE to pointOfInterest.state,
                ))
            }
        }.addOnCompleteListener { result ->
            onResult(result.isSuccessful, result.exception)
        }
    }

    suspend fun getPointsOfInterestOfCategory(categoryId: String) : List<PointOfInterest> {
        return try {
            db.collection(PointOfInterest.COLLECTION_NAME)
                .whereEqualTo(PointOfInterest.CATEGORY_ID, categoryId)
                .get()
                .await()
                .documents
                .mapNotNull {
                    it.toPointOfInterest()
                }
        } catch (e: Exception) {
            // Trate exceções ou retorne uma lista vazia em caso de erro
            emptyList()
        }
    }
    suspend fun getPointsOfInterestOfLocation(LocationId: String) : List<PointOfInterest> {
        return try {
            db.collection(PointOfInterest.COLLECTION_NAME)
                .whereEqualTo(PointOfInterest.LOCATION_ID, LocationId)
                .get()
                .await()
                .documents
                .mapNotNull {
                    it.toPointOfInterest()
                }
        } catch (e: Exception) {
            // Trate exceções ou retorne uma lista vazia em caso de erro
            emptyList()
        }
    }

    suspend fun getAllPointsOfInterest(): List<PointOfInterest> {
        return try {
            db.collection(PointOfInterest.COLLECTION_NAME)
                .get()
                .await()
                .documents
                .mapNotNull {
                    it.toPointOfInterest()
                }
        } catch (e: Exception) {
            // Trate exceções ou retorne uma lista vazia em caso de erro
            emptyList()
        }
    }
}

fun DocumentSnapshot.toPointOfInterest() : PointOfInterest {
    val id = this.getString(PointOfInterest.ID) ?: ""
    val title = this.getString(PointOfInterest.TITLE) ?: ""
    val description = this.getString(PointOfInterest.DESCRIPTION) ?: ""
    val categoryId = this.getString(PointOfInterest.CATEGORY_ID) ?: ""
    val categoryTitle = this.getString(PointOfInterest.CATEGORY_TITLE) ?: ""
    val locationId = this.getString(PointOfInterest.LOCATION_ID) ?: ""
    val locationTitle = this.getString(PointOfInterest.LOCATION_TITLE) ?: ""
    val latitude = this.getDouble(PointOfInterest.LATITUDE) ?: 0.0
    val longitude = this.getDouble(PointOfInterest.LONGITUDE) ?: 0.0
    //val image = this.getString(PointOfInterest.IMAGE)
    return PointOfInterest(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        locationId = locationId,
        locationTitle = locationTitle,
        categoryId = categoryId,
        categoryTitle = categoryTitle,
    )
}
