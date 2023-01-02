package com.olvera.dogedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.auth.AuthScreen
import com.olvera.dogedex.auth.AuthTasks
import com.olvera.dogedex.auth.AuthViewModel
import com.olvera.dogedex.model.User
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTappingRegisterButtonOpenSignUpScreen() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        composeTestRule.setContent {
            AuthScreen(onUserLoggedIn = {}, authViewModel = viewModel)
        }

        composeTestRule.onNodeWithTag(testTag = "login-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag(testTag = "register-button").performClick()
        composeTestRule.onNodeWithTag(testTag = "sign-up-button").assertIsDisplayed()

    }

    @Test
    fun testEmailErrorShowsIfTappingLoginButtonAndNotEmail() {

        class FakeAuthRepository : AuthTasks {
            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        composeTestRule.setContent {
            AuthScreen(onUserLoggedIn = {}, authViewModel = viewModel)
        }

        composeTestRule.onNodeWithTag(testTag = "login-button").performClick()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field-error").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field").performTextInput("olvera@gmail.com")
        composeTestRule.onNodeWithTag(testTag = "login-button").performClick()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "password-field-error").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "password-field").performTextInput("12345")


    }

}