package com.example.newsappassignment.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsappassignment.data.database.entity.NewsArticleEntity

/**
 * Data Access Object (DAO) for accessing and managing news article data in the local database.
 */
@Dao
interface NewsArticleDao {

    /**
     * Inserts news articles into the local database.
     * Replaces existing articles if there's a conflict.
     * @param newsArticle List of news articles to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticles(newsArticle: List<NewsArticleEntity>)

    /**
     * Retrieves all news articles from the local database.
     * @return List of all news articles stored in the database.
     */
    @Query("SELECT * FROM article")
    fun fetchNewsArticles(): List<NewsArticleEntity>

    /**
     * Clears all news articles from the local database.
     */
    @Query("DELETE FROM article")
    suspend fun clearAll()

    /**
     * Deletes the primary key index associated with the 'article' table from the local database.
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'article'")
    suspend fun deletePrimaryKeyIndex()
}