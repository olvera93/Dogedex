package com.olvera.dogedex.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olvera.dogedex.auth.LoginActivity
import com.olvera.dogedex.databinding.ActivitySettingsBinding
import com.olvera.dogedex.model.User

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            logout()
        }


    }

    private fun logout() {
        User.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}