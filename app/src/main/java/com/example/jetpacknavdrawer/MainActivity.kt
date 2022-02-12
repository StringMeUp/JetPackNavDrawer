package com.example.jetpacknavdrawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //vars
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var appConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //toolbarAction
        toolbar_Id.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlack))
        toolbar_Id.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        setSupportActionBar(toolbar_Id)
        //NavigationView and Navigation Controller
        navigationView = navigationView_Id
        navController = Navigation.findNavController(this, R.id.navHostFragment_Id)
        //AppBarConfiguration
        appConfig = AppBarConfiguration(
            setOf(
                R.id.navHome_Id,
                R.id.navDetails_Id,
                R.id.navDetails_Id,
                R.id.navInfo_Id,
                R.id.navSend_Id,
                R.id.navSubscriptions_Id,
                R.id.navLocation_Id
            ), drawerLayout_Id
        )

        NavigationUI.setupWithNavController(toolbar_Id, navController, appConfig)
        navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appConfig)
                || super.onSupportNavigateUp()
    }
}