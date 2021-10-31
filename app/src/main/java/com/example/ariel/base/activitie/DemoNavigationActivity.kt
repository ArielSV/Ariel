package com.example.ariel.base.activitie

import androidx.navigation.NavController

abstract class DemoNavigationActivity : BaseFragmentActivity() {

    abstract fun currentNavController(): NavController

    override fun onBackPressed() {
        onSupportNavigateUp()
    }
}