package pt.isec.amov.tp201101617120180146432019139650.data.models

import java.util.UUID

data class Locations(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    //val image: List<String?>,
    //val coordinates: Coordinates?,
    //val score: Score?,
    //val state: ApprovalState
) {
    companion object {
        const val ID = "location_id"
        const val COLLECTION_NAME = "location"
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

