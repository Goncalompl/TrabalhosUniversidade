package pt.isec.amov.tp201101617120180146432019139650.data.models

import pt.isec.amov.tp201101617120180146432019139650.utils.ApprovalState
import java.util.UUID

data class PointOfInterest(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    //val image: List<String?>,
    val latitude: Double,
    val longitude: Double,
    val categoryId: String,
    val categoryTitle: String,
    val locationId: String,
    val locationTitle: String,
    //val score: Score?,
    //val state: ApprovalState
) {
    companion object {
        const val ID = "point_of_interest_id"
        const val CATEGORY_ID = "category_id"
        const val CATEGORY_TITLE = "category_title"
        const val LOCATION_ID = "location_id"
        const val LOCATION_TITLE = "location_title"
        const val COLLECTION_NAME = "point_of_interest"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val IMAGE = "image"
        const val COORDINATES = "coordinates"
        const val SCORE = "score"
        const val STATE = "state"
    }
}