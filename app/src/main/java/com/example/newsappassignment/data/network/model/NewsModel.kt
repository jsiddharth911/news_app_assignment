package com.example.newsappassignment.data.network.model

/**
 * Represents a model containing a list of news articles and the status of the news retrieval
 * operation.
 * @property articles The list of news articles retrieved.
 * @property status The status of the news retrieval operation.
 */
data class NewsModel(
    val articles: List<Article>,
    val status: String
)
