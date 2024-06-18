package com.example.happytails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.mainFragment)
                    true
                }

                R.id.navigation_favorites -> {
                    navController.navigate(R.id.favoriteFragment)
                    true
                }

                R.id.navigation_login -> {
                    navController.navigate(R.id.loginFragment)

                    true
                }

                else -> false
            }
        }

        userViewModel.isConected.observe(
            this,
            Observer { updateNavigationVisibility(bottomNavigationView, it) })
    }

    private fun updateNavigationVisibility(
        bottomNavigationView: BottomNavigationView,
        connected: Boolean?
    ) {
        val connectMenu = bottomNavigationView.menu.findItem(R.id.navigation_login)
        connectMenu.isVisible = !((connected) == true)
    }
}

