package com.example.calculationseoulcomicland.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculationseoulcomicland.MainActivity
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.DefineValue

class TotalFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {

    }
}