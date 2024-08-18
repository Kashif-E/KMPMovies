package com.kashif.common.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastMemberDomainModel(
    @SerialName("cast_id") val castId: Int,
    val character: String,
    @SerialName("credit_id") val creditId: String,
    val gender: Int,
    val id: Int,
    val order: Int,
    @SerialName("profile_path") val profilePath: String?
)

