package com.kashif.common.data.remote.dto

import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.util.Constants
import com.kashif.common.domain.util.Constants.TMDB_HD_IMAGE_URL
import com.kashif.common.domain.util.Constants.TMDB_IMAGE_URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.round

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
    @SerialName("vote_count") val voteCount: Int,
    val genre: Genre?=Genre(0,"")
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
        releaseDate = formatDate(this.releaseDate),
        title = this.title,
        video = this.video,
        voteAverage = formatVoteAverage(this.voteAverage).toFloat(),
        voteCount = this.voteCount.toString(),
        genre = emptyList()
    )

fun List<Result>.asDomainModel() = map { it.asDomainModel() }

fun formatDate(inputDate: String): String {
    val parts = inputDate.split("-")
    val year = parts[0]
    val month = parts[1].toInt()

    val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    val monthName = months[month - 1]

    return "$monthName, $year"
}
fun formatVoteAverage(voteAverage: Double): Double {
    return (round(voteAverage * 10) / 10)
}

