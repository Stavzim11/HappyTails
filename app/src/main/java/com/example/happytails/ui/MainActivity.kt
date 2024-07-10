package com.example.happytails.ui

import android.app.Application
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.happytails.R
import com.example.happytails.repository.implementations.DogsRepositoryImpl
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.ui.main_screen.MainFragmentViewModel
import com.example.happytails.ui.main_screen.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

   // private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

       // ViewModel setup
        val userViewModel: UserViewModel by viewModels {
            UserViewModel.UserViewModelFactory(UserRepositoryImpl())
        }
        //userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
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

                else -> false
            }
        }

       // Observe user connection status and update navigation visibility
        userViewModel.isConected.observe(this) {
            updateNavigationVisibility(bottomNavigationView, it)
        }
   }


    private fun updateNavigationVisibility(
        bottomNavigationView: BottomNavigationView,
        connected: Boolean?
    ) {
        val connectMenu = bottomNavigationView.menu.findItem(R.id.navigation_login)
        connectMenu.isVisible = connected != true
    }
}