package com.example.calculationseoulcomicland

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculationseoulcomicland.databinding.ActivityMainBinding
import com.example.calculationseoulcomicland.ui.admin.StockFragment
import com.example.calculationseoulcomicland.ui.admin.TotalFragment
import com.example.calculationseoulcomicland.ui.user.CalculationFragment
import com.example.calculationseoulcomicland.ui.user.HomeFragment
import com.example.calculationseoulcomicland.ui.user.ProfileFragment
import com.example.calculationseoulcomicland.util.PreferenceUtil

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = "MainActivity."

    companion object{
        lateinit var preferences: PreferenceUtil
    }

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(mBinding.root)

        setBottomNavigationView();

        if (savedInstanceState == null) {
            mBinding.bottomNavigationBar.selectedItemId = R.id.home
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setBottomNavigationView() {
        mBinding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, HomeFragment()).commit()
                    Log.d(LOG_TAG, "setBottomNavigationView: home")
                    true
                }
                R.id.calculation -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, CalculationFragment()).commit()
                    Log.d(LOG_TAG, "setBottomNavigationView: calculation")
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, ProfileFragment()).commit()
                    Log.d(LOG_TAG, "setBottomNavigationView: profile")
                    true
                }
                R.id.stock -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, StockFragment()).commit()
                    Log.d(LOG_TAG, "setBottomNavigationView: stock")
                    true
                }
                R.id.total -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, TotalFragment()).commit()
                    Log.d(LOG_TAG, "setBottomNavigationView: total")
                    true
                }
                else -> false
            }
        }
    }
}