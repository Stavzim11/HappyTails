package com.example.happytails.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.happytails.R
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val ActivityVm :MainActivityViewModel by viewModels{
        MainActivityViewModel.MainActivityViewModelFactory(UserRepositoryImpl())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

       // Apply window insets to handle system bars
       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       // Set up navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

       // Set up bottom navigation item selection listener
       bottomNavigationView.setOnItemSelectedListener { item ->
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
                R.id.navigation_logOut -> {
                    navController.navigate(R.id.logoutFragment)
                    true
                }
                else -> false
            }
        }

//    ActivityVm.isConected.observe(
//            this,
//            Observer { updateNavigationVisibility(bottomNavigationView, it) })
    }


    private fun updateNavigationVisibility(
        bottomNavigationView: BottomNavigationView,
        connected: Boolean?
    ) {
        val isConected:Boolean= ((connected) == true)
        var connectMenu = bottomNavigationView.menu.findItem(R.id.navigation_login)
        connectMenu.isVisible = !isConected
        connectMenu = bottomNavigationView.menu.findItem(R.id.navigation_logOut)
        connectMenu.isVisible=isConected

    }
}