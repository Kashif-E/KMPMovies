package com.kashif.paging

data class PagingConfig(
    val pageSize: Int,
    val totalPages: Int = Int.MAX_VALUE
)