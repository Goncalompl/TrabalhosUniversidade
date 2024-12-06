package pt.isec.amov.tp201101617120180146432019139650.data.models

import java.util.UUID

data class Score(
    val id: String = UUID.randomUUID().toString(),
    val rate: Int,
    val comment: String? = null,
    val image: String? = null,
) {
    companion object {
        const val COLLECTION_NAME = "score"
        const val RATE = "rate"
        const val TITLE = "comment"
        const val IMAGE = "image"
    }
}