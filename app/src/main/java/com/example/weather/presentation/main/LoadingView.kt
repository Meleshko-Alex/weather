package com.example.weather.presentation.main

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