package com.kashif.common.data.dto

data class PaginatedData<T>(val items: List<T>, val hasMore: Boolean)