package jp.ceed.android.mylapslogger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.constants.AppConstants
import jp.ceed.android.mylapslogger.databinding.ActivityMainBinding
import jp.ceed.android.mylapslogger.util.AppSettings
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var drawerLayout: DrawerLayout

    @Inject
    lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
//        if (appSettings.isUseCompose()) {
//            navController.setGraph(R.navigation.compose_navigation)
//        } else {
//            navController.setGraph(R.navigation.view_navigation)
//        }
        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        checkLocationPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }

    private fun checkLocationPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                AppConstants.RequestCode.REQUEST_LOCATION
            )
        }
    }
}