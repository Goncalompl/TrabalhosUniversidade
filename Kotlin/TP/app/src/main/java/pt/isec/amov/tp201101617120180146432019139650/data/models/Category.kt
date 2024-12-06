package pt.isec.amov.tp201101617120180146432019139650.data.models

import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String?,
    val image: String?,
) {
    companion object {
        const val COLLECTION_NAME = "category"
        const val ID = "category_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
    }
}