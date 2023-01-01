package com.olvera.dogedex

import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.auth.AuthTasks
import com.olvera.dogedex.auth.AuthViewModel
import com.olvera.dogedex.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*


class AuthViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun testLoginValidationsCorrect() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1, "olvera@gmail.com", "")
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1, "", "")
                )
            }
        }

        val viewModel = AuthViewModel(authRepository = FakeAuthRepository())

        viewModel.signIn("", "1234")
        assertEquals(R.string.email_is_not_valid, viewModel.emailError.value)

        viewModel.signIn("olvera@gmail.com", "")
        assertEquals(R.string.password_must_not_be_empty, viewModel.passwordError.value)

    }

    @Test
    fun testLoginStatesCorrect() {

        val fakeUser = User(1, "olvera@gmail.com", "")


        class FakeAuthRepository : AuthTasks {
            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1, "", "")
                )
            }
        }

        val viewModel = AuthViewModel(authRepository = FakeAuthRepository())

        viewModel.signIn("olvera@gmail.com", "1234")
        assertEquals(fakeUser.email, viewModel.user.value?.email)

    }

    @Test
    fun testSignUpValidationsCorrect() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1, "olvera@gmail.com", "")
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {

                return ApiResponseStatus.Success(
                    User(1, "olvera@gmail.com", "32iuir")
                )

            }

        }

        val viewModel = AuthViewModel(authRepository = FakeAuthRepository())

        viewModel.signUp("", "12345", "12345")
        assertEquals(R.string.email_is_not_valid, viewModel.emailError.value)

        viewModel.signUp("olvera@gmail.com", "", "12345")
        assertEquals(R.string.password_must_not_be_empty, viewModel.passwordError.value)

        viewModel.signUp("olvera@gmail.com", "12345", "")
        assertEquals(R.string.password_must_not_be_empty, viewModel.confirmPasswordError.value)

        viewModel.signUp("olvera@gmail.com", "12345", "3424")
        assertEquals(R.string.passwords_do_not_match, viewModel.confirmPasswordError.value)
    }

    @Test
    fun testSignUpStatesCorrect() {

        val fakeUser = User(1, "olvera@gmail.com", "")


        class FakeAuthRepository : AuthTasks {
            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1, "", ""))
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }
        }

        val viewModel = AuthViewModel(authRepository = FakeAuthRepository())

        viewModel.signUp("olvera@gmail.com", "12345", "12345")
        assertEquals(fakeUser.email, viewModel.user.value?.email)
    }
}