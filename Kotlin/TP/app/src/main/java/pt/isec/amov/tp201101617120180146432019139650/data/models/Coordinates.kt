package pt.isec.amov.tp201101617120180146432019139650.data.models

import pt.isec.amov.tp201101617120180146432019139650.utils.Accuracy
import pt.isec.amov.tp201101617120180146432019139650.utils.CoordinatesProvider
import java.util.UUID

data class Coordinates(
    val id: String = UUID.randomUUID().toString(),
    val latitude: Double,
    val longitude: Double,
    val origin: CoordinatesProvider,
    val accuracy: Accuracy?,
) {
    companion object {
        const val COLLECTION_NAME = "location"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val ORIGIN = "origin"
        const val ACCURACY = "accuracy"

    }
}