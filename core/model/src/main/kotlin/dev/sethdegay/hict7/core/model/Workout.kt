package dev.sethdegay.hict7.core.model

data class Workout(
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    val bookmarked: Boolean,
    val dateCreated: Long,
    val dateModified: Long,
    val exercises: List<Exercise>,
)
