package com.kashif.common.data.remote.dto

data class PaginatedData<T>(val items: List<T>, val hasMore: Boolean)