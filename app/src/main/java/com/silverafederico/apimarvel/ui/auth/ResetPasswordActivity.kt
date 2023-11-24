package com.silverafederico.apimarvel.ui.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.silverafederico.apimarvel.R
import com.silverafederico.apimarvel.databinding.ActivityRegisterBinding
import com.silverafederico.apimarvel.databinding.ActivityResetPasswordBinding
import com.silverafederico.apimarvel.utils.AuthUtils

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authUtils: AuthUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth
        authUtils = AuthUtils(this)

        binding.btnReset.setOnClickListener {
            val email = binding.editTextEmail.text.toString()

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                    }
                }

            binding.btnCancel.setOnClickListener {
                showLoginScreen()
            }
        }
    }

    private fun showLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}