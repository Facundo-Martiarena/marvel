package com.silverafederico.apimarvel.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseUser

class AuthUtils(private val context: Context) {

    fun updateUI(user: FirebaseUser?, text: String) {
        if (user != null) {
            showSuccessDialog("${text} successful")
        } else {
            showErrorDialog("${text} failed. Please try again.")
        }
    }

    private fun showSuccessDialog(message: String) {
        showDialog("Success", message)
    }

    private fun showErrorDialog(message: String) {
        showDialog("Error", message)
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}