package com.kashif.common.data.remote.dto

import com.kashif.common.domain.model.CastMemberDomainModel
import com.kashif.common.domain.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsResponse(
    val id: Int,
    val cast: List<CastMember>,
)

@Serializable
data class CastMember(
    @SerialName("cast_id") val castId: Int,
    val character: String,
    @SerialName("credit_id") val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    @SerialName("profile_path") val profilePath: String?
)

fun MovieCreditsResponse.toDomainModel() = this
    .cast.map {
        CastMemberDomainModel(
            castId = it.castId,
            character = it.name + " as " + it.character,
            creditId = it.creditId,
            profilePath = Constants.TMDB_HD_IMAGE_URL + it.profilePath,
            gender = it.gender,
            id = it.id,
            order = it.order
        )
    }.sortedBy { it.order }.filter { it.profilePath != null }
