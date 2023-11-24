package com.silverafederico.apimarvel.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.silverafederico.apimarvel.databinding.ActivityLoginBinding
import com.silverafederico.apimarvel.ui.home.HomeActivity
import com.silverafederico.apimarvel.utils.AuthUtils

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authUtils: AuthUtils
    private var textAlert : String = "Login"

    private var loginAttempts = 0
    private val maxLoginAttempts = 3
    private val lockoutTimeMillis = 1 * 60 * 1000 // 5 minutos en milisegundos
    private var isAccountLocked = false
    private lateinit var lockoutTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        authUtils = AuthUtils(this)

        lockoutTimer = object : CountDownTimer(lockoutTimeMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                isAccountLocked = false
                loginAttempts = 0
                binding.signBtn.isEnabled = true // Habilitar el botón después de que el temporizador ha finalizado
            }
        }

        binding.signBtn.setOnClickListener {
            val email = binding.editTextEmailInput.text.toString()
            val password = binding.editTextPasswordInput.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            val user = auth.currentUser
                            authUtils.updateUI(user, textAlert)
                            showHomeScreen()
                        } else {
                            loginAttempts++
                            if (loginAttempts >= maxLoginAttempts) {
                                isAccountLocked = true
                                lockoutTimer.start()
                                handleAccountLockout()
                            } else {
                                authUtils.updateUI(null, textAlert)
                            }
                        }
                    }
            }
        }

        binding.regBtn.setOnClickListener {
            showRegisterScreen()
        }
    }

    private fun handleAccountLockout() {

        binding.signBtn.isEnabled = false
        Toast.makeText(this, "Account locked. Try again later.", Toast.LENGTH_SHORT).show()
    }

    private fun showRegisterScreen() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun showHomeScreen(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        lockoutTimer.cancel()
    }

}