package com.olvera.dogedex.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.dogedex.R
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthTasks

) : ViewModel() {

    var user = mutableStateOf<User?>(null)
        private set

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    var emailError = mutableStateOf<Int?>(null)
        private set

    var passwordError = mutableStateOf<Int?>(null)
        private set

    var confirmPasswordError = mutableStateOf<Int?>(null)
        private set


    fun signUp(email: String, password: String, passwordConfirmation: String) {

        when {
            email.isEmpty() -> {
                emailError.value = R.string.email_is_not_valid
            }
            password.isEmpty() -> {
                passwordError.value = R.string.password_must_not_be_empty
            }
            passwordConfirmation.isEmpty() -> {
                confirmPasswordError.value = R.string.password_must_not_be_empty
            }
            password != passwordConfirmation -> {
                passwordError.value = R.string.passwords_do_not_match
                confirmPasswordError.value = R.string.passwords_do_not_match

            }
        }

        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirmation))
        }
    }

    fun signIn(email: String, password: String) {

        when {
            email.isEmpty() -> emailError.value = R.string.email_is_not_valid
            password.isEmpty() -> passwordError.value = R.string.password_must_not_be_empty

            else -> {
                viewModelScope.launch {
                    status.value = ApiResponseStatus.Loading()
                    handleResponseStatus(authRepository.signIn(email, password))
                }
            }
        }


    }

    fun resetErrors() {
        emailError.value = null
        passwordError.value = null
        confirmPasswordError.value = null
    }

    private fun handleResponseStatus(apiResponseStatus: com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.User>) {
        if (apiResponseStatus is com.olvera.dogedex.core.api.ApiResponseStatus.Success) {
            user.value = apiResponseStatus.data!!
        }
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

}