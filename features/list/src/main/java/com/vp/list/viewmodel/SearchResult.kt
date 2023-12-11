package com.vp.list.viewmodel

import com.vp.list.model.ListItem

sealed class SearchResult(
    val items: List<ListItem>,
    val totalResult: Int
) {

    object Error : SearchResult(emptyList(), 0)

    data class Success(val listItems: List<ListItem>, val total: Int) :
        SearchResult(listItems, total)

    object InProgress : SearchResult(emptyList(), 0)

}