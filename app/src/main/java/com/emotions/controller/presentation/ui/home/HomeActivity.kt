package com.emotions.controller.presentation.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.emotions.controller.R
import com.emotions.controller.databinding.ActivityHomeBinding
import com.emotions.controller.presentation.ui.base.BindingActivity

class HomeActivity : BindingActivity<ActivityHomeBinding>(),
    NavController.OnDestinationChangedListener {

    override val layoutId: Int = R.layout.activity_home
    override val activity: AppCompatActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            (supportFragmentManager.findFragmentById
                (R.id.nav_home_fragment) as NavHostFragment).navController
        )
    }

    override fun onStart() {
        super.onStart()
        binding.navHomeFragment.findNavController()
            .addOnDestinationChangedListener(this)
    }

    override fun onStop() {
        binding.navHomeFragment.findNavController()
            .removeOnDestinationChangedListener(this)
        super.onStop()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.toolbar.title = destination.label
        when (destination.id) {
            R.id.tabsEmotionFragment,
            R.id.tabHistoryFragment,
            R.id.tabStatisticsFragment,
            R.id.tabSettingsFragment -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setHomeButtonEnabled(false)
            }
            else -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeButtonEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}