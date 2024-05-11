package com.example.newsappassignment.data.network.model

/**
 * Represents a news article retrieved from an API response.
 * @property author The author of the article.
 * @property content The content of the article.
 * @property description A short description or summary of the article.
 * @property publishedAt The date and time when the article was published.
 * @property source The source of the article.
 * @property title The title of the article.
 * @property url The URL to the full article.
 * @property urlToImage The URL to the image associated with the article.
 */
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
