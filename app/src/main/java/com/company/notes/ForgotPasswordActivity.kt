package com.company.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.notes.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupActionBar()

        binding.btnSubmit.setOnClickListener {
            val email: String = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this@ForgotPasswordActivity, "Enter Email", Toast.LENGTH_LONG).show()

//                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            Toast.makeText(this@ForgotPasswordActivity,"Reset link Sent Successfully", Toast.LENGTH_LONG).show()
//                            finish()
                        } else {
                            Toast.makeText(this@ForgotPasswordActivity,"Error", Toast.LENGTH_LONG).show()


                        }
                    }
            }
        }
    }

//    private fun setupActionBar() {
//        setSupportActionBar(binding.toolbarForgotPasswordActivity)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
//        }
//
//        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed()}
//    }
}