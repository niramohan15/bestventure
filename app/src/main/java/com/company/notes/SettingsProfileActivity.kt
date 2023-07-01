package com.company.notes


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.company.notes.databinding.ActivitySettingsProfileBinding
import com.company.notes.firestore.FireStoreClass
import com.company.notes.model.User
import com.company.notes.utils.Constants
import com.company.notes.utils.GlideLoader

import com.google.firebase.auth.FirebaseAuth

class SettingsProfileActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivitySettingsProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupActionBar()

        println(getUserDetails())
        getUserDetails()

        binding.tvEdit.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    private fun getUserDetails() {
//        showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().getUserDetails(this@SettingsProfileActivity)
    }

    fun userDetailsSuccess(user: User) {

        mUserDetails = user

//        hideProgressDialog()
        binding.apply {
            GlideLoader(this@SettingsProfileActivity).loadUserPicture(user.image, binding.ivUserPhoto)

            tvName.text = "${user.firstName} ${user.lastName}"
            tvGender.text = user.gender
            tvEmail.text = user.email
            tvMobileNumber.text = "${user.mobile}"
        }


    }

//    private fun setupActionBar() {
//        val toolbar = binding.toolbarSettingsActivity
//        setSupportActionBar(toolbar)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.img)
//        }
//
//        toolbar.setNavigationOnClickListener { onBackPressed() }
//    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_edit -> {
                    val intent = Intent(this@SettingsProfileActivity, UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails)
                    startActivity(intent)
                    finish()
                }
                R.id.btn_logout -> {
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this@SettingsProfileActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}