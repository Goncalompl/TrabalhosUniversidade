package pt.isec.amov.tp201101617120180146432019139650.data.repos

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import pt.isec.amov.tp201101617120180146432019139650.data.models.Locations

class LocationRepository {

    private val db = Firebase.firestore

    fun saveLocation(locations: Locations, onResult: (Boolean, Throwable?) -> Unit) {
        val c = db.collection(Locations.COLLECTION_NAME).document(locations.id)
        db.runTransaction { transaction ->
            val doc = transaction.get(c)
            if(doc.exists()){
                //if exists, updates document
                transaction.update(c, Locations.TITLE, locations.title)
                transaction.update(c, Locations.DESCRIPTION, locations.description)
                transaction.update(c,Locations.LATITUDE,locations.latitude)
                transaction.update(c,Locations.LONGITUDE,locations.longitude)
                //transaction.update(c, Location.IMAGE, location.image)

                //transaction.update(c, Location.STATE, location.state)
                null
            } else {
                //if not, creates one
                c.set(hashMapOf(
                    Locations.ID to locations.id,
                    Locations.TITLE to locations.title,
                    Locations.DESCRIPTION to locations.description,
                    Locations.LATITUDE to locations.latitude,
                    Locations.LONGITUDE to locations.longitude
                    //Location.IMAGE to location.image,
                    //Location.STATE to location.state,
                ))
            }
        }.addOnCompleteListener { result ->
            onResult(result.isSuccessful, result.exception)
        }
    }

    /*
suspend fun getLocationDetails(locationId: String): Location {
    // Aqui você pode substituir por lógica de acesso ao banco de dados ou outra fonte de dados
    // Simplesmente retorna uma Location de exemplo com base no locationId
    return when (locationId) {
        "1" -> Location(
            id = "1",
            title = "Local Exemplo 1",
            description = "Descrição detalhada do Local 1.",
            latitude = 23.456,
            longitude = 10.789
        )
        "2" -> Location(
            id = "2",
            title = "Local Exemplo 2",
            description = "Descrição detalhada do Local 2.",
            latitude = 34.567,
            longitude = 12.345
        )
        else -> throw IllegalArgumentException("LocationId inválido: $locationId")
    }
}

fun aaagetAllLocations() : List<Location> {
    val locs = mutableListOf<Location>()
    db.collection(Location.COLLECTION_NAME).get()
        .addOnSuccessListener { documentsSnapshot ->
            for (document in documentsSnapshot) {
                locs.add(document.toObject<Location>())
                locs.add(
                    Location(
                    id = document.id,
                    title = document.get(Location.TITLE).toString(),
                    description = document.get(Location.DESCRIPTION).toString(),
                        latitude = (document.get(Location.LATITUDE) as? Number)?.toDouble() ?: 0.0,
                    longitude = (document.get(Location.LONGITUDE) as? Number)?.toDouble() ?: 0.0
                    )
                )
            }
        }
        .addOnFailureListener{ result ->
            Log.i("TAG", "getAllLocations: $result")
        }
    return locs
}

 */

    suspend fun getAllLocations(): List<Locations> {
        return try {
            db.collection(Locations.COLLECTION_NAME)
                .get()
                .await()
                .documents
                .mapNotNull {
                    it.toLocation()
                }
        } catch (e: Exception) {
            // Trate exceções ou retorne uma lista vazia em caso de erro
            emptyList()
        }
    }
}

fun DocumentSnapshot.toLocation() : Locations {
    val id = this.getString(Locations.ID) ?: ""
    val title = this.getString(Locations.TITLE) ?: ""
    val description = this.getString(Locations.DESCRIPTION) ?: ""
    val latitude = this.getDouble(Locations.LATITUDE) ?: 0.0
    val longitude = this.getDouble(Locations.LONGITUDE) ?: 0.0
    //val image = this.getString(Location.IMAGE)
    val loc = Locations(
        id = id, title = title, description = description, latitude = latitude, longitude = longitude
    )
    Log.i("TAG Loc rep", "toLocation: $loc")
    return loc
}
