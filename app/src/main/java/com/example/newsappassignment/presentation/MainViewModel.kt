package com.example.newsappassignment.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappassignment.commonutils.NetworkUtils
import com.example.newsappassignment.commonutils.Results
import com.example.newsappassignment.data.database.entity.NewsArticleEntity
import com.example.newsappassignment.data.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the news data and state.
 * @property newsRepository The repository for fetching news data.
 * @property appContext The application context.
 */
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val newsCache = mutableListOf<NewsArticleEntity>()
    private val _state: MutableStateFlow<Results<List<NewsArticleEntity>>> =
        MutableStateFlow(Results.Success(emptyList()))

    val state: StateFlow<Results<List<NewsArticleEntity>>> = _state

    init {
        // Check network connection and fetch news data accordingly
        val isNetworkConnected = NetworkUtils.isNetworkAvailable(appContext)
        if (isNetworkConnected) {
            fetchNewsFromApi()
        } else {
            fetchNewsFromDb()
        }
    }

    /**
     * Fetches news data from the local database.
     */
    private fun fetchNewsFromDb() {
        viewModelScope.launch {
            newsRepository.provideNewsFromDb()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _state.value = Results.Failure(e.message ?: "An unknown error occurred")
                }
                .collect { result ->
                    _state.value = result
                }
        }
    }

    /**
     * Fetches news data from the API.
     */
    private fun fetchNewsFromApi() {
        viewModelScope.launch {
            // If cache is not empty, emit the cached news data immediately
            if (newsCache.isNotEmpty()) {
                _state.value = Results.Success(newsCache)
            }

            newsRepository.provideNewsFromApi()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _state.value = Results.Failure(e.message ?: "An unknown error occurred")
                }
                .collect { result ->
                    when (result) {
                        is Results.Success -> {
                            // Cache the fetched news data
                            newsCache.clear()
                            newsCache.addAll(result.data)
                            _state.value = result
                        }

                        is Results.Failure -> {
                            _state.value = result
                        }
                    }
                }
        }
    }
}