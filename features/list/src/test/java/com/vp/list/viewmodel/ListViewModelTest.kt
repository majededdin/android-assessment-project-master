package com.vp.list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.vp.list.model.ListItem
import com.vp.list.model.SearchResponse
import com.vp.list.service.SearchService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.internal.verification.AtLeast
import retrofit2.mock.Calls
import java.io.IOException

class ListViewModelTest {
    @JvmField
    @Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun shouldReturnErrorState() {
        //given
        val searchService = mock(SearchService::class.java)
        `when`(searchService.search(anyString(), anyInt())).thenReturn(Calls.failure(IOException()))
        val listViewModel = ListViewModel(searchService)

        //when
        listViewModel.searchMoviesByTitle("title", 1)

        //then
        assertThat(listViewModel.observeMovies().value!!).isEqualTo(SearchResult.Error)
    }

    @Test
    fun shouldReturnInProgressState() {
        //given
        val searchService = mock(SearchService::class.java)
        `when`(searchService.search(anyString(), anyInt())).thenReturn(
            Calls.response(
                mock(
                    SearchResponse::class.java
                )
            )
        )
        val listViewModel = ListViewModel(searchService)
        val mockObserver = mock(
            Observer::class.java
        ) as Observer<SearchResult>
        listViewModel.observeMovies().observeForever(mockObserver)

        //when
        listViewModel.searchMoviesByTitle("title", 1)

        //then
        verify(mockObserver).onChanged(SearchResult.InProgress)
    }

    @Test
    fun shouldReturnSuccessState() { //given
        val searchService = mock(SearchService::class.java)
        val listItem = ListItem("mock", "mock", "mock", "mock")
        val listItems: MutableList<ListItem> = ArrayList()
        listItems.add(listItem.copy())
        listItems.add(listItem.copy())
        val searchResponseMock = SearchResponse("True", listItems, listItems.size)

        `when`(
            searchService.search(
                anyString(),
                anyInt()
            )
        ).thenReturn(Calls.response(searchResponseMock))

        val listViewModel = ListViewModel(searchService)
        val mockObserver =
            mock(Observer::class.java) as Observer<SearchResult>
        val captor =
            ArgumentCaptor.forClass(
                SearchResult::class.java
            )
        listViewModel.observeMovies().observeForever(mockObserver)

        listViewModel.searchMoviesByTitle("title", 1)

        verify(mockObserver, AtLeast(1)).onChanged(captor.capture())
        Assert.assertEquals(SearchResult.InProgress, captor.allValues[0])
        Assert.assertEquals(
            SearchResult.Success(listItems, listItems.size),
            captor.allValues[1]
        )
    }


}