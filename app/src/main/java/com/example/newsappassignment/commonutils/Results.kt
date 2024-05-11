package com.example.newsappassignment.commonutils

/**
 * A sealed class representing the result of an operation, which can be either success or failure.
 * @param T The type of data encapsulated within the result.
 */
sealed class Results<T> {
    /**
     * Represents a successful result with data.
     * @param data The data associated with the successful result.
     */
    data class Success<T>(val data: T) : Results<T>()

    /**
     * Represents a failed result with an error message.
     * @param errorMessage The error message associated with the failure.
     */
    data class Failure<T>(val errorMessage: String) : Results<T>()
}