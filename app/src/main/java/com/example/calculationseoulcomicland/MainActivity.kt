package com.example.calculationseoulcomicland

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculationseoulcomicland.databinding.ActivityMainBinding
import com.example.calculationseoulcomicland.ui.user.CalculationFragment
import com.example.calculationseoulcomicland.ui.user.HomeFragment
import com.example.calculationseoulcomicland.ui.user.ProfileFragment
import com.example.calculationseoulcomicland.util.PreferenceUtil

class MainActivity : AppCompatActivity() {

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
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setBottomNavigationView() {
        mBinding.bottomNavigationBar.setOnClickListener { item ->
            when (item.id) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, HomeFragment()).commit()
                    true
                }
                R.id.calculation -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, CalculationFragment()).commit()
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}