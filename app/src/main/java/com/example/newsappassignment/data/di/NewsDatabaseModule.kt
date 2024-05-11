package com.example.newsappassignment.data.di

import android.content.Context
import androidx.room.Room
import com.example.newsappassignment.data.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Module responsible for providing the NewsDatabase instance using Dagger Hilt.
 */
@Module
@InstallIn(SingletonComponent::class)
class NewsDatabaseModule {

    /**
     * Provides a singleton instance of the NewsDatabase.
     * @param context The application context.
     * @return A singleton instance of the NewsDatabase.
     */
    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news.db"
        ).fallbackToDestructiveMigration().build()
    }
}