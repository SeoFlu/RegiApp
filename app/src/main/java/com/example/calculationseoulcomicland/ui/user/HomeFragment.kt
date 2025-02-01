package com.example.calculationseoulcomicland.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculationseoulcomicland.MainActivity
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.DefineValue
import com.example.calculationseoulcomicland.databinding.FragmentHomeBinding
import com.example.calculationseoulcomicland.databinding.FragmentProfileBinding

class HomeFragment : Fragment() {
    private val LOG_TAG = "HomeFragment."

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        MainActivity.preferences.setBoolean(DefineValue.PREFERENCE_KEY_FRAGMENT_ADMIN, false)

        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()

        return binding.root
    }

    private fun initViews(){
        mBinding!!.homeAdminLayout.setOnLongClickListener{
            MainActivity.preferences.setBoolean(DefineValue.PREFERENCE_KEY_FRAGMENT_ADMIN, true)
            true
        }
    }



}