package com.olvera.dogedex.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olvera.dogedex.R
import com.olvera.dogedex.databinding.FragmentLoginBinding
import com.olvera.dogedex.isValidEmail

class LoginFragment : Fragment() {

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onSignInFieldsValidated(email: String, password: String)

    }

    private lateinit var loginFragmentActions: LoginFragmentActions
    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        binding.apply {
            loginRegisterButton.setOnClickListener {
                loginFragmentActions.onRegisterButtonClick()
            }

            loginButton.setOnClickListener {
                validateFields()
            }
        }

        return binding.root
    }

    private fun validateFields() {
        binding.apply {
            emailInput.error = ""
            passwordInput.error = ""
        }

        val email = binding.emailEdit.text.toString()

        if (!isValidEmail(email)) {
            binding.emailEdit.error = getString(R.string.email_is_not_valid)
            return

        }

        val password = binding.passwordEdit.text.toString()

        if (password.isEmpty()) {
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        loginFragmentActions.onSignInFieldsValidated(email, password)

    }

}