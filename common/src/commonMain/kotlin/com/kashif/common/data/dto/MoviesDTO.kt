package com.kashif.common.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MoviesDTO(
    @SerialName("results") val movies: List<Result>,
)
@Serializable
data class Result(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String,
    val id: Int,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)
