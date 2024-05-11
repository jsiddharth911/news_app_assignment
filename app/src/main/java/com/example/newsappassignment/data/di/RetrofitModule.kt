package com.example.newsappassignment.data.di

import com.example.newsappassignment.data.network.newsservice.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module responsible for providing the Retrofit instance and the associated API service using Dagger Hilt.
 */
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    /**
     * Provides a singleton instance of Retrofit configured with base URL and Gson converter.
     * @return A singleton instance of Retrofit.
     */
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://candidate-test-data-moengage.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides a singleton instance of the NewsApi interface using the Retrofit instance.
     * @param retrofit The Retrofit instance.
     * @return A singleton instance of the NewsApi interface.
     */
    @Singleton
    @Provides
    fun provideApiServiceInstance(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }
}