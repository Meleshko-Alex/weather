package com.example.weather.presentation

interface LoadingView {
    /**
     * Displays loading state
     */
    fun displayLoading()

    /**
     * Hides loading state
     */
    fun hideLoading()
}