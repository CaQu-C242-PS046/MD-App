package com.example.capstone.ui.change

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstone.R

class ChangePassword : AppCompatActivity() {
    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var showHideOldPasswordIcon: ImageView
    private lateinit var showHideNewPasswordIcon: ImageView
    private lateinit var showHideConfirmPasswordIcon: ImageView
    private lateinit var confirmChangeButton: Button
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)


        oldPasswordEditText = findViewById(R.id.old_password)
        newPasswordEditText = findViewById(R.id.new_password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        showHideOldPasswordIcon = findViewById(R.id.showHidePasswordIcon)
        showHideNewPasswordIcon = findViewById(R.id.showHidePasswordIcon2)
        showHideConfirmPasswordIcon = findViewById(R.id.showHidePasswordIcon3)
        confirmChangeButton = findViewById(R.id.confirm_change_button)
        backButton = findViewById(R.id.backButton)


        setupPasswordVisibilityToggle(oldPasswordEditText, showHideOldPasswordIcon)
        setupPasswordVisibilityToggle(newPasswordEditText, showHideNewPasswordIcon)
        setupPasswordVisibilityToggle(confirmPasswordEditText, showHideConfirmPasswordIcon)


        setupPasswordValidation()


        backButton.setOnClickListener { onBackPressed() }


        confirmChangeButton.setOnClickListener { changePassword() }
    }

    private fun setupPasswordVisibilityToggle(editText: EditText, iconView: ImageView) {
        var isPasswordVisible = false
        iconView.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val inputType = if (isPasswordVisible) {
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            editText.inputType = inputType
            editText.typeface = editText.typeface // Preserve cursor position
            editText.setSelection(editText.text.length)

            val visibilityIcon = if (isPasswordVisible) {
                R.drawable.baseline_remove_red_eye_24
            } else {
                R.drawable.baseline_visibility_off_24
            }
            iconView.setImageDrawable(ContextCompat.getDrawable(this, visibilityIcon))
        }
    }

    private fun setupPasswordValidation() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePasswords()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        oldPasswordEditText.addTextChangedListener(textWatcher)
        newPasswordEditText.addTextChangedListener(textWatcher)
        confirmPasswordEditText.addTextChangedListener(textWatcher)
    }

    private fun validatePasswords() {
        val oldPassword = oldPasswordEditText.text.toString()
        val newPassword = newPasswordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        val isOldPasswordValid = oldPassword.isNotBlank()
        val isNewPasswordValid = isValidPassword(newPassword)
        val isConfirmPasswordValid = newPassword == confirmPassword

        confirmChangeButton.isEnabled = isOldPasswordValid &&
                isNewPasswordValid &&
                isConfirmPasswordValid
    }

    private fun isValidPassword(password: String): Boolean {
        // Basic password validation
        return password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() }
    }

    private fun changePassword() {
        val oldPassword = oldPasswordEditText.text.toString()
        val newPassword = newPasswordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()


        if (validatePasswordChange(oldPassword, newPassword, confirmPassword)) {

            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {

            Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePasswordChange(oldPassword: String, newPassword: String, confirmPassword: String): Boolean {
        // TODO: Implement actual password validation
        // This should typically involve:
        // 1. Checking if the old password matches the current password
        // 2. Ensuring the new password meets security requirements
        // 3. Updating the password in your authentication system
        return oldPassword.isNotBlank() &&
                newPassword == confirmPassword &&
                isValidPassword(newPassword)
    }
}