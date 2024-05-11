package com.example.newsappassignment.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a news article entity stored in the local database.
 * @param id The unique identifier of the news article.
 * @param author The author of the news article.
 * @param content The content of the news article.
 * @param description The description of the news article.
 * @param publishedAt The publish date of the news article.
 * @param title The title of the news article.
 * @param url The URL of the complete news article.
 * @param urlToImage The URL to the image associated with the news article.
 * @param sourceId The unique identifier of the news source.
 * @param sourceName The name of the news source.
 */
@Entity(tableName = "article")
data class NewsArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val sourceId: String?,
    val sourceName: String
)
