package com.olvera.dogedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.auth.AuthTasks
import com.olvera.dogedex.auth.LoginComposeActivity
import com.olvera.dogedex.di.AuthTasksModule
import com.olvera.dogedex.core.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalMaterialApi
@UninstallModules(AuthTasksModule::class)
@ExperimentalFoundationApi
@ExperimentalCoilApi
class LoginActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<LoginComposeActivity>()

    class FakeAuthRepository @Inject constructor() : AuthTasks {
        override suspend fun signIn(email: String, password: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.User> {
            return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                com.olvera.dogedex.core.model.User(1L, "olvera9@gmail.com", "1knjn3223")
            )
        }

        override suspend fun signUp(
            email: String,
            password: String,
            passwordConfirmation: String
        ): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.User> {
            TODO("Not yet implemented")
        }

    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class AuthTasksTestModule {

        @Binds
        abstract fun bindAuthTasks(fakeAuthRepository: FakeAuthRepository): AuthTasks
    }


    @Test
    fun mainActivityOpensAfterUserLogin() {
        val context = composeTestRule.activity

        composeTestRule.onNodeWithText(context.getString(R.string.login)).assertIsDisplayed()

        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field")
            .performTextInput("olvera@gmail.com")

        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "password-field")
            .performTextInput("1234")

        composeTestRule.onNodeWithText(context.getString(R.string.login)).performClick()

        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
    }


}