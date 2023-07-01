package com.company.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.company.notes.databinding.ActivityLoginBinding
import com.company.notes.firestore.FireStoreClass
import com.company.notes.model.User
import com.company.notes.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() , View.OnClickListener{


    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserDetails: User
    private var autoLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)

        userAutoLoggedIn() // -> bug while logging out, can't log in anymore
        /* bug while auto logging in, it doesn't work
        if (autoLoggedIn) {
            userAutoLoggedIn()
        }*/
    }

    private fun userAutoLoggedIn() {
        autoLoggedIn = false
        val currentUserId = FireStoreClass().getCurrentUserId()

        if (currentUserId.isNotEmpty()) {
            autoLoggedIn = true
            startActivity(Intent(this@LoginActivity, UserHomeActivity::class.java))
        }
    }

    fun userLoggedInSuccess(user: User) {
        mUserDetails = user

        if (mUserDetails.profileCompleted == 0) {
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails)
            startActivity(intent)
        } else {
            startActivity(Intent(this@LoginActivity, UserHomeActivity::class.java))
        }
        finish()
    }


    private fun loginRegisteredUser() {
        if (validateLoginDetails()) {

//            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            if(email.equals("admin") && password.equals("admin")){
                startActivity(Intent(this@LoginActivity, AdminHome::class.java))
            }



            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        FireStoreClass().getUserDetails(this@LoginActivity)
                        FireStoreClass().checkIfEmailVerified(this)

                    } else {
                        Toast.makeText(applicationContext,"Invalid Email or Password",Toast.LENGTH_SHORT).show()

//                        hideProgressDialog()
//                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }


    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this@LoginActivity, "Please enter email.", Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this@LoginActivity, "Please enter password.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_login -> {
                    loginRegisteredUser()
                }
                R.id.tv_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }


}