package com.example.newsappassignment.data.network.newsservice

import com.example.newsappassignment.data.network.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface representing a News API service for fetching news data.
 */
interface NewsApi {

    /**
     * Retrieves news data from the API.
     * @return A Response containing a NewsModel object representing the news data.
     */
    @GET("Android/news-api-feed/staticResponse.json")
    suspend fun getNews(): Response<NewsModel>
}
