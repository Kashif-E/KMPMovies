package com.kashif.common.data.paging

import com.kashif.paging.PagingConfig
import kotlin.reflect.KFunction0
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

fun <T : Any> CoroutineScope.paginate(
    useCase: () -> PagingSource<Int, T>,
    pagingConfig: PagingConfig = PagingConfig(pageSize = 20),
): Pair<MutableStateFlow<Result<List<T>>>, KFunction0<Unit>> {
    val pagedData = MutableStateFlow<Result<List<T>>>(Result.Loading)
    var currentPage = 1
    var hasCompletedLoadingPages = false

    fun fetchNextPage() {
        if (hasCompletedLoadingPages) return

        launch {
            try {
                val pagingSource = useCase()
                val loadResult =
                    pagingSource.load(PagingSource.LoadParams(currentPage, pagingConfig.pageSize))
                if (loadResult is PagingSource.LoadResult.Success) {
                    currentPage = loadResult.nextKey ?: (currentPage + 1)
                    hasCompletedLoadingPages =
                        loadResult.nextKey == null || currentPage > pagingConfig.totalPages

                    val currentData = (pagedData.value as? Result.Success)?.data ?: emptyList()
                    pagedData.update { Result.Success(currentData + loadResult.data) }
                } else if (loadResult is PagingSource.LoadResult.Error) {
                    pagedData.update {
                        Result.Error(
                            (loadResult.throwable.cause?.message ?: loadResult.throwable.message)
                                ?: "Something went wrong, please try again."
                        )
                    }
                }
            } catch (exception: Exception) {
                pagedData.update {
                    Result.Error(exception.message ?: "Something went wrong, please try again.")
                }
            }
        }
    }

    fetchNextPage()

    return pagedData to ::fetchNextPage
}
