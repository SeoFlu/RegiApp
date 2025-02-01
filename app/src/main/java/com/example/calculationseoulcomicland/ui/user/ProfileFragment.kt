package com.example.calculationseoulcomicland.ui.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.provider.ContactsContract.Data
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
    private lateinit var profileAdapter: ProfileListAdapter
    private val arrItems = mutableListOf<Profile>()

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
        profileAdapter = ProfileListAdapter(context!!)
        mBinding!!.profileList.layoutManager = LinearLayoutManager(context)
        profileAdapter.setItemClickListener(object :
            ProfileListAdapter.OnItemClickListener{
            override fun onClick(v: View, pos: Int) {
                when(v.id){
                    R.id.profile_remove -> {
                        Log.d(LOG_TAG, "onClick: remove $pos ")
                        removeAuthor(pos)
                    }
                    R.id.profile_modify -> {
                        Log.d(LOG_TAG, "onClick: modify $pos ")
                        modifyAuthor(pos)
                    }
                }
            }
        }
        )
        mBinding!!.profileList.adapter = profileAdapter

        mBinding!!.profileAddAuthor.setOnClickListener{
            showAddAuthorView()
        }
    }

    private fun showAddAuthorView(){
        var info : String
        mBinding?.profileAddView?.root?.visibility = View.VISIBLE
        mBinding?.profileAddView?.authorClose?.setOnClickListener{
            mBinding?.profileAddView?.root?.visibility = View.GONE
        }
        mBinding?.profileAddView?.authorAdd?.setOnClickListener{
            mBinding?.profileAddView?.root?.visibility = View.GONE
            val authorName : String = mBinding?.profileAddView?.authorAddName?.text.toString()
//            Log.d(LOG_TAG, "showAddAuthorView authorName : "+authorName)
            if(authorName.isNotEmpty() && !authorName.equals("") ){
                if( checkExistAuthor(authorName) ){
                    Toast.makeText(context, "작가명이 중복 입니다.",Toast.LENGTH_SHORT).show()
                } else {
                    val profileString = MainActivity.preferences.getString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, "")
//                Log.d(LOG_TAG, "showAddAuthorView profileString : "+profileString)
                    if(profileString == ""){
                        val profile = Profile(1, authorName, null)
                        info = profile.profileToString(profile)
//                    Log.d(LOG_TAG, "showAddAuthorView info 0 : "+info)
                    }
                    else {
                        val profile = Profile(DataUtil.StringToProfileItemList(profileString).size+1, authorName, null)
                        info = "$profileString&${profile.profileToString(profile)}"
//                    Log.d(LOG_TAG, "showAddAuthorView info 1 : "+info)
                    }

                    MainActivity.preferences.setString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, info)
                    resetListItem()
                }
            } else {
                Toast.makeText(context, "작가명이 비어있습니다.",Toast.LENGTH_SHORT).show()
            }
        }
        mBinding?.profileAddView?.authorAddName?.setText("")
    }

    private fun checkExistAuthor(authorText : String) : Boolean{
        var checkVal = false

        val profilePrefString = MainActivity.preferences.getString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, "")
        val profileArr = DataUtil.StringToProfileItemList(profilePrefString)
        for(profileInfo in profileArr){
            if(profileInfo.name == authorText)
                checkVal = true
        }
        return checkVal
    }

    private fun removeAuthor(pos : Int){
        Log.d(LOG_TAG, "removeAuthor() $pos")
        val profilePrefString = MainActivity.preferences.getString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, "")
        val profileArr = DataUtil.StringToProfileItemList(profilePrefString)
        if(pos < profileArr.size)
            profileArr.removeAt(pos)

        MainActivity.preferences.setString(DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, DataUtil.ProfileItemListToString(profileArr))
        resetListItem()
    }

    private fun modifyAuthor(pos : Int){
        Log.d(LOG_TAG, "modifyAuthor() $pos")
        Toast.makeText(context, "수정은 준비중 입니다. \n삭제 후 새로 추가 해주세요.",Toast.LENGTH_SHORT).show()
    }

    private fun initPreference(){
        MainActivity.preferences = PreferenceUtil(requireActivity())
        MainActivity.preferences.setBoolean(DefineValue.PREFERENCE_KEY_FRAGMENT_ADMIN, false)
    }

    private fun initData() {
        resetListItem()
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
                } else {
                    Toast.makeText(this.context, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            })

        alertDialog.show()
    }

    private fun resetListItem(){

        arrItems.apply {
            arrItems.clear()

            val profileList = DataUtil.StringToProfileItemList( MainActivity.preferences.getString(
                DefineValue.PREFERENCE_KEY_PROFILE_INFO_STRING, ""))
            for( item in profileList ){
                add(item)
            }
            profileAdapter.differ.submitList(arrItems)

            Log.d(LOG_TAG, "resetListItem arrItems : "+arrItems)
            profileAdapter.notifyDataSetChanged()
        }
    }
}