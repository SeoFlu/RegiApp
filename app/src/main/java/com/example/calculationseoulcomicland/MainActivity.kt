package com.example.calculationseoulcomicland

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calculationseoulcomicland.data.DefineValue
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

    private var prefListener: OnSharedPreferenceChangeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(mBinding.root)
        setBottomNavigationView();
        initPreference();

        if (savedInstanceState == null) {
            mBinding.bottomNavigationBar.selectedItemId = R.id.home
            supportFragmentManager.beginTransaction().replace(R.id.main_container, HomeFragment()).commit()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.getPreference().unregisterOnSharedPreferenceChangeListener(prefListener)
    }

    fun setBottomNavigationView() {
        mBinding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    preferences.setString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG, DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_HOME)
                    Log.d(LOG_TAG, "setBottomNavigationView: home")
                    true
                }
                R.id.calculation -> {
                    preferences.setString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG, DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_CALCULATE)
                    Log.d(LOG_TAG, "setBottomNavigationView: calculation")
                    true
                }
                R.id.profile -> {
                    preferences.setString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG, DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_PROFILE)
                    Log.d(LOG_TAG, "setBottomNavigationView: profile")
                    true
                }
                R.id.stock -> {
                    preferences.setString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG, DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_STOCK)
                    Log.d(LOG_TAG, "setBottomNavigationView: stock")
                    true
                }
                R.id.total -> {
                    preferences.setString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG, DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_TOTAL)
                    Log.d(LOG_TAG, "setBottomNavigationView: total")
                    true
                }
                else -> false
            }
        }
    }

    fun initPreference(){

        resetPreference()

        prefListener =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                Log.d(LOG_TAG, key + "SELECTED")
                if (key == DefineValue.PREFERENCE_KEY_FRAGMENT_TAG) {
                    var tag = preferences.getString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG, "")
                    if(tag.isNotEmpty()){
                        when(tag){
                            DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_HOME -> {
                                supportFragmentManager.beginTransaction().replace(R.id.main_container, HomeFragment()).commit()
                            }
                            DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_CALCULATE -> {
                                supportFragmentManager.beginTransaction().replace(R.id.main_container, CalculationFragment()).commit()
                            }
                            DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_PROFILE -> {
                                supportFragmentManager.beginTransaction().replace(R.id.main_container, ProfileFragment()).commit()
                            }
                            DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_STOCK -> {
                                supportFragmentManager.beginTransaction().replace(R.id.main_container, StockFragment()).commit()
                            }
                            DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_TOTAL -> {
                                supportFragmentManager.beginTransaction().replace(R.id.main_container, TotalFragment()).commit()
                            }
                        }
                    }
                }
            }
        preferences.getPreference().registerOnSharedPreferenceChangeListener(prefListener)
    }

    fun resetPreference(){
        preferences.setString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG, DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_HOME)
    }
}