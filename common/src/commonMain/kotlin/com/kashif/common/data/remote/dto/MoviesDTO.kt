package com.kashif.common.data.remote.dto

import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.util.Constants
import com.kashif.common.domain.util.Constants.TMDB_HD_IMAGE_URL
import com.kashif.common.domain.util.Constants.TMDB_IMAGE_URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesDTO(
    @SerialName("results") val movies: List<Result>,
)

@Serializable
data class Result(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String? ="",
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
        backdropPath =  Constants.TMBD_BACKDROP_URL + this.backdropPath,
        id = this.id,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity.toString(),
        posterPath =TMDB_IMAGE_URL +  this.posterPath,
        hdPosterPath = TMDB_HD_IMAGE_URL + this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage.toFloat(),
        voteCount = this.voteCount.toString())

fun List<Result>.asDomainModel() = map { it.asDomainModel() }
