package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.company.notes.databinding.ActivityRegisterBinding
import com.company.notes.firestore.FireStoreClass
import com.company.notes.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupActionBar()

        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }


    private fun registerUser() {
        if (validateRegisterDetails()) {

//            showProgressDialog(resources.getString(R.string.please_wait))

            val firstName: String = binding.etFirstName.text.toString().trim()
            val lastName: String = binding.etLastName.text.toString().trim()
            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result.user!!

                        val user = User(
                            firebaseUser.uid,
                            firstName,
                            lastName,
                            email
                        )

                        FireStoreClass().registerUser(this@RegisterActivity, user)
                        FireStoreClass().sendEmailVerification(this@RegisterActivity)

                    } else {
//                        hideProgressDialog()

                        Toast.makeText(
                            this@RegisterActivity,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    }

    private fun validateRegisterDetails(): Boolean {
        binding.apply {
            return when {
                TextUtils.isEmpty(etFirstName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter First Name",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                TextUtils.isEmpty(etLastName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter Last Name",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                TextUtils.isEmpty(etEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter Email Address",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                TextUtils.isEmpty(etEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter Email Address",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                TextUtils.isEmpty(etPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                TextUtils.isEmpty(etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter Conform Password",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                etPassword.text.toString().trim { it <= ' ' } != etConfirmPassword.text.toString()
                    .trim { it <= ' ' } -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password Must Be Same",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                !cbTermsAndCondition.isChecked -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Accept the Terms and Conditions",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                else -> {
//                    Toast.makeText(
//                        this@RegisterActivity,
//                        "Successfully Registered",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    true
                }
            }
        }
    }


    fun userRegisteredSuccess() {

        Toast.makeText(
            this@RegisterActivity,
            "Successfully Registered",
            Toast.LENGTH_SHORT
        ).show()
        val i = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(i)

//        FirebaseAuth.getInstance().signOut()
//        finish()
    }

//    private fun setupActionBar() {
//        setSupportActionBar(binding.toolbarRegisterActivity)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
//        }
//
//        binding.toolbarRegisterActivity.setNavigationOnClickListener { onBackPressed() }
//    }


    companion object {
        const val USER_ID = "user_id"
        const val EMAIL = "email"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
    }



}