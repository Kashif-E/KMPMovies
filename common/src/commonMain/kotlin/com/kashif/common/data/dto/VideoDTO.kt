package com.kashif.common.data.dto

import com.kashif.common.domain.model.VideoDomainModel
import kotlinx.serialization.Serializable

@Serializable data class VideoDTO(val id: Int, val results: List<TrailerResult>)

@Serializable
data class TrailerResult(
    val name: String,
    val key: String,
    val site: String,
    val official: Boolean,
)

suspend fun VideoDTO.asDomainModel(): VideoDomainModel {
    val officialTrailer = this.results.first { trailerResult -> trailerResult.official }

    return VideoDomainModel(
        name = officialTrailer.name,
        site = officialTrailer.site,
        key = officialTrailer.key,
    )
}
