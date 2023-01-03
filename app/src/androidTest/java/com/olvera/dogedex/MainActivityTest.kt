package com.olvera.dogedex

import androidx.camera.core.ImageProxy
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.di.ClassifierTasksModule
import com.olvera.dogedex.di.DogTasksModule
import com.olvera.dogedex.doglist.DogTasks
import com.olvera.dogedex.machinelearning.ClassifierTasks
import com.olvera.dogedex.machinelearning.DogRecognition
import com.olvera.dogedex.main.MainActivity
import com.olvera.dogedex.core.model.Dog
import com.olvera.dogedex.testutils.EspressoIdlingResource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@UninstallModules(DogTasksModule::class, ClassifierTasksModule::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @get:Rule(order = 2)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    class FakeDogRepository @Inject constructor() : DogTasks {
        override suspend fun getDogCollection(): com.olvera.dogedex.core.api.ApiResponseStatus<List<com.olvera.dogedex.core.model.Dog>> {
            return com.olvera.dogedex.core.api.ApiResponseStatus.Loading()
        }

        override suspend fun addDogToUser(dogId: Long): com.olvera.dogedex.core.api.ApiResponseStatus<Any> {
            TODO("Not yet implemented")
        }

        override suspend fun getDogByMlId(mlDogId: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.Dog> {
            return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                com.olvera.dogedex.core.model.Dog(
                    89,
                    70,
                    "Chow Chow",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    inCollection = true
                )
            )
        }

    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DogTasksTestModule {

        @Binds
        abstract fun bindDogTasks(
            fakeDogRepository: FakeDogRepository
        ): DogTasks

    }

    class FakeClassifierRepository @Inject constructor() : ClassifierTasks {
        override suspend fun recognizeImage(imageProxy: ImageProxy): List<DogRecognition> {
            return listOf(
                DogRecognition("1", 80.0f)
            )
        }

    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ClassifierTasksTestModule {

        @Binds
        abstract fun bindClassifierTasks(
            fakeClassifierRepository: FakeClassifierRepository
        ): ClassifierTasks
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)

    }

    @Test
    fun showAllFabs() {
        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.settings_fab)).check(matches(isDisplayed()))

    }

    @Test
    fun dogListOpensWhenClickingButton() {
        onView(withId(R.id.dog_list_fab)).perform(click())

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val string = context.getString(R.string.my_dog_collection)

        composeTestRule.onNodeWithText(string).assertIsDisplayed()

    }


    @Test
    fun whenRecognizingDogDetailsScreenOpens() {
        onView(withId(R.id.take_photo_fab)).perform(click())
        composeTestRule.onNodeWithTag(testTag = "close-details-screen-fab").assertIsDisplayed()
    }

}