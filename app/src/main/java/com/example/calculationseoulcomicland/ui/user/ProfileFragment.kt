package com.example.calculationseoulcomicland.ui.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculationseoulcomicland.MainActivity
import com.example.calculationseoulcomicland.R
import com.example.calculationseoulcomicland.data.DefineValue
import com.example.calculationseoulcomicland.data.Profile
import com.example.calculationseoulcomicland.databinding.FragmentProfileBinding
import com.example.calculationseoulcomicland.ui.view.ProfileListAdapter
import com.example.calculationseoulcomicland.util.DataUtil
import com.example.calculationseoulcomicland.util.PreferenceUtil

class ProfileFragment : Fragment() {

    private val LOG_TAG = "ProfileFragment."

    private var mBinding: FragmentProfileBinding? = null
    private val binding get() = mBinding!!
    private var arrItems = ArrayList<Profile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        initView()
        initPreference()
        initData()
//        showInputPasswordDialog()

        return binding.root
    }

    private fun initView(){
        mBinding?.profileAddAuthor?.setOnClickListener{
            showAddAuthorView()
        }
    }

    private fun showAddAuthorView(){
        mBinding?.profileAddView?.root?.visibility = View.VISIBLE
        mBinding?.profileAddView?.authorClose?.setOnClickListener{
            mBinding?.profileAddView?.root?.visibility = View.GONE
        }
        mBinding?.profileAddView?.authorAdd?.setOnClickListener{
            mBinding?.profileAddView?.root?.visibility = View.GONE
            val authorName : String = mBinding?.profileAddView?.authorAddName?.text.toString()
//            Log.d(LOG_TAG, "showAddAuthorView authorName : "+authorName)
            if(authorName.isNotEmpty() && !authorName.equals("") ){
                val profileString = MainActivity.preferences.getString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, "")
//                Log.d(LOG_TAG, "showAddAuthorView profileString : "+profileString)
                if(profileString == ""){
                    val profile = Profile(1, authorName, null)
                    val info = profile.profileToString(profile)
//                    Log.d(LOG_TAG, "showAddAuthorView info 0 : "+info)
                    MainActivity.preferences.setString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, info)
                    resetListItem(DataUtil.StringToProfileItemList(info))
                }
                else {
                    val profile = Profile(DataUtil.StringToProfileItemList(profileString).size+1, authorName, null)
                    val info = "$profileString&${profile.profileToString(profile)}"
//                    Log.d(LOG_TAG, "showAddAuthorView info 1 : "+info)
                    MainActivity.preferences.setString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, info)
                    resetListItem(DataUtil.StringToProfileItemList(info))
                }
            } else {
                Toast.makeText(context, "작가명이 비어있습니다.",Toast.LENGTH_SHORT).show()
            }
        }
        mBinding?.profileAddView?.authorAddName?.setText("")
    }

    private fun initPreference(){
        MainActivity.preferences = PreferenceUtil(requireActivity())
    }

    private fun initData() {
        // test
        MainActivity.preferences.setString(
            DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, "")

        arrItems = DataUtil.StringToProfileItemList(
            MainActivity.preferences.getString(
                DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, ""))
        resetListItem(arrItems)
    }

    private fun showInputPasswordDialog(){
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.profile_question_dialog, null)
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Password")
            .setCancelable(false)
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                val pref = PreferenceUtil(context!!)
                pref.setString(DefineValue.PREFERENCE_KEY_FRAGMENT_TAG,DefineValue.PREFERENCE_KEY_FRAGMENT_TAG_HOME)
                dialog.dismiss()
            })
            .setPositiveButton("입력",DialogInterface.OnClickListener{ dialog, which ->
                val pw = mDialogView.findViewById<EditText>(R.id.profile_dialog_pw).text
                if(pw.equals("250112")){
                    dialog.dismiss()
                    mBinding!!.profileList.layoutManager = LinearLayoutManager(context)
                    if(arrItems.isNotEmpty())
                        mBinding!!.profileList.adapter = ProfileListAdapter(arrItems)
                } else {
                    Toast.makeText(this.context, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            })

        alertDialog.show()
    }

    private fun resetListItem(arrInfo : ArrayList<Profile>){
        mBinding!!.profileList.layoutManager = LinearLayoutManager(context)
        if(arrInfo.isNotEmpty()){
            mBinding!!.profileList.adapter = ProfileListAdapter(arrInfo)
            mBinding!!.profileList.adapter?.notifyDataSetChanged()
        }
        Log.d(LOG_TAG, "resetListItem: "+arrInfo)
    }
}