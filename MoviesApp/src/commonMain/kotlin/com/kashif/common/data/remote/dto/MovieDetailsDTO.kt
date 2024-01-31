package com.kashif.common.data.remote.dto

import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDTO(
    @SerialName("backdrop_path")
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)

@Serializable
data class ProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)

fun MovieDetailsDTO.asDomainModel() = MoviesDomainModel(
    backdropPath = Constants.TMBD_BACKDROP_URL + this.backdropPath,
    id = this.id,
    originalTitle = this.title,
    overview = this.overview,
    popularity = this.popularity.toString(),
    posterPath = Constants.TMDB_IMAGE_URL + this.posterPath,
    hdPosterPath = Constants.TMDB_HD_IMAGE_URL + this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    video = this.video,
    voteAverage = this.voteAverage.toFloat(),
    voteCount = this.voteCount.toString()
)