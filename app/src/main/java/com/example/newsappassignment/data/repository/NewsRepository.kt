package com.example.newsappassignment.data.repository

import com.example.newsappassignment.commonutils.Results
import com.example.newsappassignment.data.database.NewsDatabase
import com.example.newsappassignment.data.database.entity.NewsArticleEntity
import com.example.newsappassignment.data.network.model.Article
import com.example.newsappassignment.data.network.newsservice.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class responsible for managing data flow between the News API and the local database.
 * @property newsApi The News API service for fetching news data.
 * @property newsDatabase The local Room database for storing news data.
 */
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
) {

    /**
     * Provides news data from the API.
     * @return A Flow emitting Results containing a list of news articles from the API.
     */
    fun provideNewsFromApi(): Flow<Results<List<NewsArticleEntity>>> = flow {
        try {
            val response = newsApi.getNews()
            if (response.isSuccessful && response.body() != null) {
                val articles = response.body()!!.articles.map { article ->
                    article.toNewsArticleEntity()
                }
                saveArticlesToDatabase(articles)
                emit(Results.Success(articles))
            } else {
                emit(Results.Failure("Something went wrong"))
            }
        } catch (e: Exception) {
            emit(Results.Failure(e.message ?: "An unknown error occurred"))
        }
    }

    /**
     * Maps an Article object to a NewsArticleEntity object.
     * @return The mapped NewsArticleEntity object.
     */
    private fun Article.toNewsArticleEntity() = NewsArticleEntity(
        id = 0, // You may need to generate an appropriate ID here
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage,
        sourceId = source.id,
        sourceName = source.name
    )

    /**
     * Saves articles to the local database.
     */
    private suspend fun saveArticlesToDatabase(articles: List<NewsArticleEntity>) {
        withContext(Dispatchers.IO) {
            newsDatabase.newsDao.clearAll()
            newsDatabase.newsDao.deletePrimaryKeyIndex()
            newsDatabase.newsDao.insertNewsArticles(articles)
        }
    }

    /**
     * Provides news data from the local database.
     * @return A Flow emitting Results containing a list of news articles from the local database.
     */
    fun provideNewsFromDb(): Flow<Results<List<NewsArticleEntity>>> = flow {
        try {
            val articleData = newsDatabase.newsDao.fetchNewsArticles()
            if(articleData.isNotEmpty()) {
                emit(Results.Success(articleData))
            } else {
                emit(Results.Failure("Something went wrong"))
            }
        } catch (e: Exception) {
            emit(Results.Failure("Something went wrong"))
        }
    }
}