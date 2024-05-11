package com.example.newsappassignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsappassignment.data.database.dao.NewsArticleDao
import com.example.newsappassignment.data.database.entity.NewsArticleEntity

/**
 * Represents the Room database for storing news articles.
 * @param newsDao The Data Access Object (DAO) for accessing news article data.
 */
@Database(entities = [NewsArticleEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract val newsDao: NewsArticleDao
}