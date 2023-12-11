package com.vp.list.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Response")
    val response: String,

    @SerializedName("Search")
    val search: List<ListItem> = emptyList(),

    @SerializedName("totalResults")
    val totalResults: Int
)