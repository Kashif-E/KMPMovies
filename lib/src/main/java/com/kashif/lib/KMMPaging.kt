package com.kashif.lib

data class PaginatedData<T>(
    val data: List<T>,
    val hasNextPage: Boolean
)

interface PagingSource<T> {
    suspend fun getData(pageSize: Int, page: Int): PaginatedData<T>
}

class PagingManager<T>(
    private val pagingSource: PagingSource<T>
) {
    fun getPaginatedData(pageSize: Int): Flow<PaginatedData<T>> {
        var currentPage = 0
        var hasMorePages = true

        return flow {
            while (hasMorePages) {
                val data = pagingSource.getData(pageSize, currentPage)
                currentPage++
                hasMorePages = data.hasNextPage
                emit(data)
            }
        }
    }
}