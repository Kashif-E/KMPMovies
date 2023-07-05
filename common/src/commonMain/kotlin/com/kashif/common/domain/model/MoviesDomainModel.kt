package com.kashif.common.domain.model

data class MoviesDomainModel(
    val backdropPath: String,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: String,
    val hdPosterPath: String,
)
