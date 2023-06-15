package com.example.weather.presentation.main

interface DrawerLayoutLocker {
    /**
     * Locks the navigation drawer layout.
     * When locked, a user can't open the drawer by swiping from the edge of the screen.
     */
    fun lockNavigationDrawer()

    /**
     * Unlocks the navigation drawer layout.
     * A user can open the drawer by swiping from the edge of the screen.
     */
    fun unlockNavigationDrawer()
}