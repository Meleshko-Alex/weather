package com.example.weather.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.weather.R

abstract class BaseFragment : Fragment(), DrawerLayoutLocker {
    private lateinit var navDrawer: DrawerLayout
    var actionBar: ActionBar? = null
    lateinit var window: Window
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navDrawer = requireActivity().findViewById(R.id.drawerLayout)
        navController = (requireActivity() as MainActivity).navController
        actionBar = (requireActivity() as MainActivity).supportActionBar
        window = requireActivity().window
    }

    /**
     * Sets up action bar style, e.g. title text, background colors etc.
     */
    open fun setUpActionBar() {

    }

    /**
     * Sets up status bar styles, e.g. text color, background color etc.
     */
    open fun setUpStatusBar() {

    }


    override fun lockNavigationDrawer() {
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }


    override fun unlockNavigationDrawer() {
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    /**
     * Helper function to display a toast message.
     *
     * @param context the context in which the toast should be displayed.
     * @param msg the message to be displayed in the toast.
     * @param length the duration for which the toast should be shown. Can be one of the values: [Toast.LENGTH_SHORT] or [Toast.LENGTH_LONG]
     */
    fun makeToast(context: Context, msg: String, length: Int) {
        Toast.makeText(context, msg, length).show()
    }
}