package com.kashif.common.data.dto

import com.kashif.common.domain.model.MoviesDomainModel
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

fun Result.asDomainModel() =
    MoviesDomainModel(
        backdropPath = "https://image.tmdb.org/t/p/" + this.backdropPath.replace(".jpg", ".svg"),
        id = this.id,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity.toString(),
        posterPath = "https://image.tmdb.org/t/p/" + this.posterPath.replace(".jpg", ".svg"),
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage.toString(),
        voteCount = this.voteCount.toString())

fun List<Result>.asDomainModel() = map { it.asDomainModel() }
