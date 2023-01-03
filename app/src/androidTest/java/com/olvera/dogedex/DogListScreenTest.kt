package com.olvera.dogedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.doglist.DogListScreen
import com.olvera.dogedex.doglist.DogListViewModel
import com.olvera.dogedex.doglist.DogTasks
import com.olvera.dogedex.core.model.Dog
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
class DogListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun progressBarShowsWhenLoadingState() {

        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): com.olvera.dogedex.core.api.ApiResponseStatus<List<com.olvera.dogedex.core.model.Dog>> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Loading()
            }

            override suspend fun addDogToUser(dogId: Long): com.olvera.dogedex.core.api.ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(testTag = "loading-wheel").assertIsDisplayed()
    }

    @Test
    fun errorDialogShowsIfGettingDogs() {

        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): com.olvera.dogedex.core.api.ApiResponseStatus<List<com.olvera.dogedex.core.model.Dog>> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Error(messageId = R.string.there_was_an_error)
            }

            override suspend fun addDogToUser(dogId: Long): com.olvera.dogedex.core.api.ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(testTag = "error-dialog").assertIsDisplayed()
    }

    @Test
    fun dogListShowsIfSuccessGettingDogs() {

        val dog1Name = "Chihuahua"
        val dog2Name = "Husky"

        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): com.olvera.dogedex.core.api.ApiResponseStatus<List<com.olvera.dogedex.core.model.Dog>> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    listOf(
                        com.olvera.dogedex.core.model.Dog(
                            1,
                            1,
                            dog1Name,
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            inCollection = true
                        ),
                        com.olvera.dogedex.core.model.Dog(
                            2,
                            23,
                            dog2Name,
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            inCollection = false
                        )
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): com.olvera.dogedex.core.api.ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-${dog1Name}").assertIsDisplayed()
        composeTestRule.onNodeWithText(useUnmergedTree = true, text = "23").assertIsDisplayed()

    }


}