package com.kashif.common.domain.model

import kotlinx.serialization.Serializable

@Serializable

data class CastMemberDomainModel(
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val order: Int,
    val profilePath: String?
): UUID()

